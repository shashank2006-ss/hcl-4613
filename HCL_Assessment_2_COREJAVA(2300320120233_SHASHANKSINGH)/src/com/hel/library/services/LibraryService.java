package com.hel.library.services;

import com.hel.library.exception.BorrowLimitExceededException;
import com.hel.library.exception.DuplicateIsbnException;
import com.hel.library.exception.ItemNotFoundException;
import com.hel.library.exception.LibraryException;
import com.hel.library.exception.MemberNotFoundException;
import com.hel.library.model.Book;
import com.hel.library.model.Dvd;
import com.hel.library.model.LibraryItem;
import com.hel.library.model.Magazine;
import com.hel.library.model.Member;
import com.hel.library.repository.InMemoryRepository;
import com.hel.library.repository.Repository;
import com.hel.library.util.CollectionUtils;
import com.hel.library.util.SearchCache;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/** All library business rules: catalogue, members, borrowing, wait lists, search and reports. */
public class LibraryService {

    public static final int MAX_BORROWED_ITEMS = 3;

    private static final List<String> ITEM_TYPES = List.of(Book.TYPE_NAME, Magazine.TYPE_NAME, Dvd.TYPE_NAME);
    private static final String LINE = System.lineSeparator();

    private final Repository<LibraryItem, Integer> items = new InMemoryRepository<>(LibraryItem::getId);
    private final Repository<Member, Integer> members = new InMemoryRepository<>(Member::getId);
    private final Set<String> registeredIsbns = new HashSet<>();
    private final Map<Integer, Queue<Member>> waitLists = new HashMap<>();
    private final Map<Integer, Member> issuedTo = new HashMap<>();
    private final SearchCache<String, List<LibraryItem>> searchCache = new SearchCache<>();
    private final Consumer<String> notifier;

    public LibraryService() {
        this(System.out::println);
    }

    public LibraryService(Consumer<String> notifier) {
        this.notifier = notifier;
    }

    // ---------- catalogue and members ----------

    public Book addBook(String title, int year, String author, String isbn) {
        String cleanIsbn = isbn == null ? "" : isbn.trim();
        if (registeredIsbns.contains(cleanIsbn)) {
            throw new DuplicateIsbnException("A book with ISBN " + cleanIsbn + " already exists");
        }
        Book book = new Book(title, year, author, cleanIsbn);
        registeredIsbns.add(book.getIsbn());
        registerItem(book);
        return book;
    }

    public Magazine addMagazine(String title, int year, int issueNumber) {
        Magazine magazine = new Magazine(title, year, issueNumber);
        registerItem(magazine);
        return magazine;
    }

    public Dvd addDvd(String title, int year, int durationMinutes) {
        Dvd dvd = new Dvd(title, year, durationMinutes);
        registerItem(dvd);
        return dvd;
    }

    private void registerItem(LibraryItem item) {
        items.save(item);
        searchCache.clear();
    }

    public Member registerMember(String name, String email) {
        Member member = new Member(name, email);
        members.save(member);
        return member;
    }

    /** Removes available items published before the given year; items on loan are kept. */
    public int removeOldItems(int beforeYear) {
        int removed = items.removeIf(item -> item.getYear() < beforeYear && item.isAvailable());
        if (removed > 0) {
            refreshIndexesAfterRemoval();
        }
        return removed;
    }

    private void refreshIndexesAfterRemoval() {
        Set<Integer> remainingIds = items.findAll().stream()
                .map(LibraryItem::getId)
                .collect(Collectors.toSet());
        waitLists.keySet().retainAll(remainingIds);

        registeredIsbns.clear();
        items.findAll().stream()
                .filter(Book.class::isInstance)
                .map(Book.class::cast)
                .forEach(book -> registeredIsbns.add(book.getIsbn()));
        searchCache.clear();
    }

    // ---------- borrowing ----------

    public LibraryItem borrowItem(int memberId, int itemId) {
        LibraryItem item = findItem(itemId);
        Member member = findMember(memberId);
        if (member.getBorrowedCount() >= MAX_BORROWED_ITEMS) {
            throw new BorrowLimitExceededException(
                    "Member " + memberId + " already holds the maximum of " + MAX_BORROWED_ITEMS + " items");
        }
        item.borrow();
        member.addBorrowedItem(item);
        issuedTo.put(itemId, member);
        removeFromWaitList(itemId, member);
        return item;
    }

    /** Returns the item and gives back the late fee owed. Notifies the first member waiting for it. */
    public double returnItem(int itemId, int daysLate) {
        LibraryItem item = findItem(itemId);
        Member holder = issuedTo.get(itemId);
        if (holder == null) {
            throw new LibraryException("Item " + itemId + " is not currently issued");
        }
        double lateFee = item.lateFee(daysLate);
        item.giveback();
        holder.removeBorrowedItem(item);
        issuedTo.remove(itemId);
        notifyNextWaitingMember(item);
        return lateFee;
    }

    // ---------- wait list ----------

    /** Adds the member to the item's queue and returns their 1-based position. */
    public int joinWaitList(int memberId, int itemId) {
        LibraryItem item = findItem(itemId);
        Member member = findMember(memberId);
        if (item.isAvailable()) {
            throw new LibraryException("Item " + itemId + " is available, borrow it instead of waiting");
        }
        if (member.equals(issuedTo.get(itemId))) {
            throw new LibraryException("Member " + memberId + " already holds item " + itemId);
        }
        Queue<Member> queue = waitLists.computeIfAbsent(itemId, id -> new ArrayDeque<>());
        if (queue.contains(member)) {
            throw new LibraryException("Member " + memberId + " is already waiting for item " + itemId);
        }
        queue.add(member);
        return queue.size();
    }

    /** Unmodifiable snapshot of the members waiting for an item, first in line first. */
    public List<Member> getWaitList(int itemId) {
        Queue<Member> queue = waitLists.get(itemId);
        return queue == null ? List.of() : List.copyOf(queue);
    }

    private void removeFromWaitList(int itemId, Member member) {
        Queue<Member> queue = waitLists.get(itemId);
        if (queue != null) {
            queue.remove(member);
            if (queue.isEmpty()) {
                waitLists.remove(itemId);
            }
        }
    }

    private void notifyNextWaitingMember(LibraryItem item) {
        Queue<Member> queue = waitLists.get(item.getId());
        if (queue != null && !queue.isEmpty()) {
            Member next = queue.peek();
            notifier.accept("Notification: item " + item.getId() + " is available again. First in line: "
                    + next.getName() + " (member " + next.getId() + ", " + next.getEmail() + ")");
        }
    }

    // ---------- queries ----------

    public List<LibraryItem> listItems() {
        return items.findAll().stream().sorted().toList();
    }

    public List<LibraryItem> listItems(Comparator<LibraryItem> order) {
        return items.findAll().stream().sorted(order).toList();
    }

    public boolean isSearchCached(String query) {
        return searchCache.containsKey(normalise(query));
    }

    public List<LibraryItem> searchByTitle(String query) {
        String key = normalise(query);
        List<LibraryItem> cached = searchCache.get(key);
        if (cached != null) {
            return cached;
        }
        List<LibraryItem> results = items.findAll().stream()
                .filter(item -> item.getTitle().toLowerCase().contains(key))
                .sorted()
                .toList();
        searchCache.put(key, results);
        return results;
    }

    /** The last few distinct search texts, oldest first. */
    public List<String> getRecentSearches() {
        return List.copyOf(searchCache.keySet());
    }

    private static String normalise(String query) {
        if (query == null || query.isBlank()) {
            throw new IllegalArgumentException("Search text must not be blank");
        }
        return query.trim().toLowerCase();
    }

    public String getStatus(LibraryItem item) {
        Member holder = issuedTo.get(item.getId());
        if (holder == null) {
            return "Available";
        }
        return "Issued to " + holder.getName() + " (member " + holder.getId() + ")";
    }

    public Map<String, Integer> countItemsByType() {
        Map<String, Integer> counts = new TreeMap<>();
        for (LibraryItem item : items.findAll()) {
            counts.merge(item.getTypeName(), 1, Integer::sum);
        }
        return Collections.unmodifiableMap(counts);
    }

    public String formatStatistics() {
        Map<String, Integer> counts = countItemsByType();
        StringBuilder text = new StringBuilder("Items per type:").append(LINE);
        for (String type : ITEM_TYPES) {
            text.append("  ").append(type).append(": ").append(counts.getOrDefault(type, 0)).append(LINE);
        }
        return text.toString();
    }

    /** Polymorphism demo: every item answers lateFee() without any type checks. */
    public String buildLateFeeDemo(int daysLate) {
        List<LibraryItem> all = listItems();
        StringBuilder text = new StringBuilder("Late fee if returned " + daysLate + " days late:").append(LINE);
        for (LibraryItem item : all) {
            text.append("  ").append(item).append(" -> ").append(item.lateFee(daysLate)).append(LINE);
        }
        text.append("Total for all items: ").append(CollectionUtils.totalFees(all, daysLate)).append(LINE);
        if (!all.isEmpty()) {
            List<Integer> years = all.stream().map(LibraryItem::getYear).toList();
            text.append("Newest publication year: ").append(CollectionUtils.max(years)).append(LINE);
        }
        return text.toString();
    }

    // ---------- report ----------

    public String buildReport() {
        StringBuilder report = new StringBuilder("LIBRARY REPORT").append(LINE);
        report.append("==============").append(LINE);
        for (LibraryItem item : listItems()) {
            report.append(item).append(" | ").append(getStatus(item)).append(LINE);
        }
        report.append(LINE).append(formatStatistics());
        return report.toString();
    }

    public void exportReport(Path file) {
        String report = buildReport();
        try (BufferedWriter writer = Files.newBufferedWriter(file)) {
            writer.write(report);
        } catch (IOException e) {
            throw new LibraryException("Could not export report to " + file, e);
        }
    }

    // ---------- lookups ----------

    private LibraryItem findItem(int itemId) {
        return items.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item " + itemId + " does not exist"));
    }

    private Member findMember(int memberId) {
        return members.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("Member " + memberId + " does not exist"));
    }
}
