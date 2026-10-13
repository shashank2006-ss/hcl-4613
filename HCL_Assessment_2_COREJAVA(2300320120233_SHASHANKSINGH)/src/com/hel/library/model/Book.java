package com.hel.library.model;

public class Book extends LibraryItem {

    public static final String TYPE_NAME = "Book";
    private static final double LATE_FEE_PER_DAY = 2.0;

    private final String author;
    private final String isbn;

    public Book(String title, int year, String author, String isbn) {
        super(title, year);
        this.author = requireText(author, "Author");
        this.isbn = requireText(isbn, "ISBN");
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
    public double lateFee(int daysLate) {
        return requireNonNegativeDays(daysLate) * LATE_FEE_PER_DAY;
    }

    @Override
    public String getTypeName() {
        return TYPE_NAME;
    }

    @Override
    public String toString() {
        return TYPE_NAME + " [" + super.toString() + ", " + author + "]";
    }
}
