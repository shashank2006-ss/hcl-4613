package com.hel.library.model;

import com.hel.library.exception.ItemNotAvailableException;
import com.hel.library.exception.LibraryException;

import java.time.Year;
import java.util.Comparator;

/**
 * Base class for everything the library lends. Ids come from a static counter,
 * equality is based on id only, and natural ordering is by title (case-insensitive).
 */
public abstract class LibraryItem implements Borrowable, Comparable<LibraryItem> {

    public static final int FIRST_ITEM_ID = 1001;
    public static final int MIN_YEAR = 1900;

    /** Newest first, then alphabetical by title. */
    public static final Comparator<LibraryItem> BY_YEAR_DESC_THEN_TITLE =
            Comparator.comparing(LibraryItem::getYear, Comparator.reverseOrder())
                    .thenComparing(LibraryItem::getTitle, String.CASE_INSENSITIVE_ORDER);

    private static int nextId = FIRST_ITEM_ID;

    private final int id;
    private final String title;
    private final int year;
    private boolean available;

    protected LibraryItem(String title, int year) {
        this.title = requireTitle(title);
        this.year = requireYear(year);
        this.id = nextId++;
        this.available = true;
    }

    /** Late fee for returning this item {@code daysLate} days after the due date. */
    public abstract double lateFee(int daysLate);

    /** Human-readable type such as "Book"; used for statistics and reports. */
    public abstract String getTypeName();

    protected static int requireNonNegativeDays(int daysLate) {
        if (daysLate < 0) {
            throw new IllegalArgumentException("Days late must not be negative: " + daysLate);
        }
        return daysLate;
    }

    protected static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value.trim();
    }

    private static String requireTitle(String title) {
        return requireText(title, "Title");
    }

    private static int requireYear(int year) {
        int currentYear = Year.now().getValue();
        if (year < MIN_YEAR || year > currentYear) {
            throw new IllegalArgumentException(
                    "Year must be between " + MIN_YEAR + " and " + currentYear + " but was " + year);
        }
        return year;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public void borrow() {
        if (!available) {
            throw new ItemNotAvailableException("Item " + id + " is not available");
        }
        available = false;
    }

    @Override
    public void giveback() {
        if (available) {
            throw new LibraryException("Item " + id + " was not issued, so it cannot be returned");
        }
        available = true;
    }

    @Override
    public int compareTo(LibraryItem other) {
        int byTitle = title.compareToIgnoreCase(other.title);
        return byTitle != 0 ? byTitle : Integer.compare(id, other.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        return o instanceof LibraryItem other && id == other.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    /** Common fields only: "id, title, year". Subclasses wrap this with their own details. */
    @Override
    public String toString() {
        return id + ", " + title + ", " + year;
    }
}
