package com.hel.library.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Member {

    private static final int FIRST_MEMBER_ID = 1;
    private static final String EMAIL_MARKER = "@";

    private static int nextId = FIRST_MEMBER_ID;

    private final int id;
    private final String name;
    private final String email;
    private final List<LibraryItem> borrowedItems;

    public Member(String name, String email) {
        this(name, email, List.of());
    }

    /** Creates a member who already holds some items (for example when restoring saved data). */
    public Member(String name, String email, Collection<? extends LibraryItem> alreadyBorrowed) {
        this.name = requireName(name);
        this.email = requireEmail(email);
        this.borrowedItems = new ArrayList<>(alreadyBorrowed);
        this.id = nextId++;
    }

    private static String requireName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Member name must not be blank");
        }
        return name.trim();
    }

    private static String requireEmail(String email) {
        if (email == null || !email.contains(EMAIL_MARKER)) {
            throw new IllegalArgumentException("Email must contain '" + EMAIL_MARKER + "': " + email);
        }
        return email.trim();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    /** Returns an unmodifiable snapshot; callers cannot change the member's list. */
    public List<LibraryItem> getBorrowedItems() {
        return List.copyOf(borrowedItems);
    }

    public int getBorrowedCount() {
        return borrowedItems.size();
    }

    public void addBorrowedItem(LibraryItem item) {
        borrowedItems.add(item);
    }

    public void removeBorrowedItem(LibraryItem item) {
        borrowedItems.remove(item);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        return o instanceof Member other && id == other.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return "Member [" + id + ", " + name + ", " + email + ", borrowed=" + borrowedItems.size() + "]";
    }
}
