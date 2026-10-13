package com.hel.library;

import com.hel.library.exception.BorrowLimitExceededException;
import com.hel.library.exception.DuplicateIsbnException;
import com.hel.library.exception.ItemNotAvailableException;
import com.hel.library.exception.ItemNotFoundException;
import com.hel.library.exception.LibraryException;
import com.hel.library.exception.MemberNotFoundException;
import com.hel.library.model.LibraryItem;
import com.hel.library.model.Member;
import com.hel.library.services.LibraryService;

import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/** Console menu for the librarian. All business rules live in {@link LibraryService}. */
public class Main {

    private static final int CHOICE_EXIT = 0;
    private static final int CHOICE_ADD_ITEM = 1;
    private static final int CHOICE_REGISTER_MEMBER = 2;
    private static final int CHOICE_BORROW = 3;
    private static final int CHOICE_RETURN = 4;
    private static final int CHOICE_JOIN_WAIT_LIST = 5;
    private static final int CHOICE_LIST_ITEMS = 6;
    private static final int CHOICE_SEARCH = 7;
    private static final int CHOICE_EXPORT_REPORT = 8;
    private static final int CHOICE_LATE_FEE_DEMO = 9;
    private static final int CHOICE_REMOVE_OLD_ITEMS = 10;

    private static final int TYPE_BOOK = 1;
    private static final int TYPE_MAGAZINE = 2;
    private static final int TYPE_DVD = 3;

    private static final int SORT_NEWEST_FIRST = 2;
    private static final String DEFAULT_REPORT_FILE = "library_report.txt";

    private final LibraryService service = new LibraryService();
    private final Scanner scanner = new Scanner(System.in);
    private boolean running = true;

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        while (running && scanner.hasNextLine()) {
            printMenu();
            try {
                handleChoice(readInt("Choice: "));
            } catch (ItemNotFoundException | MemberNotFoundException e) {
                System.out.println("Not found: " + e.getMessage());
            } catch (BorrowLimitExceededException e) {
                System.out.println("Borrow limit: " + e.getMessage());
            } catch (DuplicateIsbnException e) {
                System.out.println("Duplicate: " + e.getMessage());
            } catch (LibraryException e) {
                System.out.println("Error: " + describe(e));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input: " + e.getMessage());
            } catch (NoSuchElementException e) {
                System.out.println("Input ended, shutting down.");
                running = false;
            } finally {
                System.out.println();
            }
        }
    }

    private static String describe(LibraryException e) {
        Throwable cause = e.getCause();
        return cause == null ? e.getMessage() : e.getMessage() + " (cause: " + cause + ")";
    }

    private void printMenu() {
        System.out.println("===== Library Menu =====");
        System.out.println(CHOICE_ADD_ITEM + ". Add item");
        System.out.println(CHOICE_REGISTER_MEMBER + ". Register member");
        System.out.println(CHOICE_BORROW + ". Borrow item");
        System.out.println(CHOICE_RETURN + ". Return item");
        System.out.println(CHOICE_JOIN_WAIT_LIST + ". Join wait list");
        System.out.println(CHOICE_LIST_ITEMS + ". List items");
        System.out.println(CHOICE_SEARCH + ". Search by title");
        System.out.println(CHOICE_EXPORT_REPORT + ". Export report");
        System.out.println(CHOICE_LATE_FEE_DEMO + ". Late-fee demo (polymorphism)");
        System.out.println(CHOICE_REMOVE_OLD_ITEMS + ". Remove old items");
        System.out.println(CHOICE_EXIT + ". Exit");
    }

    private void handleChoice(int choice) {
        switch (choice) {
            case CHOICE_ADD_ITEM -> handleAddItem();
            case CHOICE_REGISTER_MEMBER -> handleRegisterMember();
            case CHOICE_BORROW -> handleBorrow();
            case CHOICE_RETURN -> handleReturn();
            case CHOICE_JOIN_WAIT_LIST -> handleJoinWaitList();
            case CHOICE_LIST_ITEMS -> handleListItems();
            case CHOICE_SEARCH -> handleSearch();
            case CHOICE_EXPORT_REPORT -> handleExport();
            case CHOICE_LATE_FEE_DEMO -> handleLateFeeDemo();
            case CHOICE_REMOVE_OLD_ITEMS -> handleRemoveOldItems();
            case CHOICE_EXIT -> {
                running = false;
                System.out.println("Goodbye!");
            }
            default -> System.out.println("Unknown choice: " + choice);
        }
    }

    private void handleAddItem() {
        int type = readInt("Type (1=Book, 2=Magazine, 3=DVD): ");
        String title = prompt("Title: ");
        int year = readInt("Year: ");
        LibraryItem item = switch (type) {
            case TYPE_BOOK -> service.addBook(title, year, prompt("Author: "), prompt("ISBN: "));
            case TYPE_MAGAZINE -> service.addMagazine(title, year, readInt("Issue number: "));
            case TYPE_DVD -> service.addDvd(title, year, readInt("Duration (minutes): "));
            default -> throw new IllegalArgumentException("Unknown item type: " + type);
        };
        System.out.println("Added: " + item);
    }

    private void handleRegisterMember() {
        Member member = service.registerMember(prompt("Name: "), prompt("Email: "));
        System.out.println("Registered: " + member);
    }

    private void handleBorrow() {
        int memberId = readInt("Member ID: ");
        int itemId = readInt("Item ID: ");
        try {
            LibraryItem item = service.borrowItem(memberId, itemId);
            System.out.println("Issued: " + item);
        } catch (ItemNotAvailableException e) {
            System.out.println("Error: " + e.getMessage());
            int position = service.joinWaitList(memberId, itemId);
            System.out.println("Added to wait list for item " + itemId + " (position " + position + ")");
        }
    }

    private void handleReturn() {
        int itemId = readInt("Item ID: ");
        int daysLate = readInt("Days late: ");
        double lateFee = service.returnItem(itemId, daysLate);
        System.out.println("Returned item " + itemId + ". Late by " + daysLate + " days, late fee: " + lateFee);
    }

    private void handleJoinWaitList() {
        int memberId = readInt("Member ID: ");
        int itemId = readInt("Item ID: ");
        int position = service.joinWaitList(memberId, itemId);
        System.out.println("Added to wait list for item " + itemId + " (position " + position + ")");
    }

    private void handleListItems() {
        int sort = readInt("Sort by (1=title, 2=newest first): ");
        List<LibraryItem> items = sort == SORT_NEWEST_FIRST
                ? service.listItems(LibraryItem.BY_YEAR_DESC_THEN_TITLE)
                : service.listItems();
        if (items.isEmpty()) {
            System.out.println("No items in the catalogue yet.");
        }
        for (LibraryItem item : items) {
            System.out.println(item + " - " + service.getStatus(item));
        }
        System.out.print(service.formatStatistics());
    }

    private void handleSearch() {
        String query = prompt("Title contains: ");
        boolean fromCache = service.isSearchCached(query);
        List<LibraryItem> results = service.searchByTitle(query);
        System.out.println(results.size() + " result(s)" + (fromCache ? " (from cache)" : ""));
        for (LibraryItem item : results) {
            System.out.println("  " + item + " - " + service.getStatus(item));
        }
        System.out.println("Recent searches: " + service.getRecentSearches());
    }

    private void handleExport() {
        String fileName = prompt("File name [" + DEFAULT_REPORT_FILE + "]: ");
        Path target = Path.of(fileName.isBlank() ? DEFAULT_REPORT_FILE : fileName.trim());
        service.exportReport(target);
        System.out.println("Report written to " + target.toAbsolutePath());
    }

    private void handleLateFeeDemo() {
        System.out.print(service.buildLateFeeDemo(readInt("Days late: ")));
    }

    private void handleRemoveOldItems() {
        int year = readInt("Remove available items published before year: ");
        System.out.println("Removed " + service.removeOldItems(year) + " item(s).");
    }

    private String prompt(String label) {
        System.out.print(label);
        return scanner.nextLine();
    }

    private int readInt(String label) {
        return Integer.parseInt(prompt(label).trim());
    }
}
