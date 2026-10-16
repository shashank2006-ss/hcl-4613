package com.hel.library.util;

import com.hel.library.model.LibraryItem;

import java.util.List;

public final class CollectionUtils {

    private CollectionUtils() {
    }

    /** Largest element by natural order. Throws if the list is null or empty. */
    public static <T extends Comparable<? super T>> T max(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("Cannot find the maximum of an empty list");
        }
        T best = list.get(0);
        for (T candidate : list) {
            if (candidate.compareTo(best) > 0) {
                best = candidate;
            }
        }
        return best;
    }

    /** Sum of late fees if every item were returned {@code daysLate} days late. */
    public static double totalFees(List<? extends LibraryItem> items, int daysLate) {
        double total = 0.0;
        for (LibraryItem item : items) {
            total += item.lateFee(daysLate);
        }
        return total;
    }
}
