package com.hel.library.model;

public class Magazine extends LibraryItem {

    public static final String TYPE_NAME = "Magazine";
    private static final double LATE_FEE_PER_DAY = 1.0;

    private final int issueNumber;

    public Magazine(String title, int year, int issueNumber) {
        super(title, year);
        if (issueNumber <= 0) {
            throw new IllegalArgumentException("Issue number must be positive: " + issueNumber);
        }
        this.issueNumber = issueNumber;
    }

    public int getIssueNumber() {
        return issueNumber;
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
        return TYPE_NAME + " [" + super.toString() + ", issue " + issueNumber + "]";
    }
}
