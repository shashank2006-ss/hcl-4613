package com.hel.library.model;

public class Dvd extends LibraryItem {

    public static final String TYPE_NAME = "DVD";
    private static final double LATE_FEE_PER_DAY = 5.0;

    private final int durationMinutes;

    public Dvd(String title, int year, int durationMinutes) {
        super(title, year);
        if (durationMinutes <= 0) {
            throw new IllegalArgumentException("Duration must be positive: " + durationMinutes);
        }
        this.durationMinutes = durationMinutes;
    }

    public int getDurationMinutes() {
        return durationMinutes;
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
        return TYPE_NAME + " [" + super.toString() + ", " + durationMinutes + " min]";
    }
}
