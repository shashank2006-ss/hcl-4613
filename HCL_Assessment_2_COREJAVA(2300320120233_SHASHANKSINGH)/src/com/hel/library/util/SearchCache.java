package com.hel.library.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Least-recently-used cache holding at most {@value #MAX_ENTRIES} entries. Access order is
 * enabled, so reading an entry marks it as fresh, and the eldest entry is dropped on overflow.
 */
public class SearchCache<K, V> extends LinkedHashMap<K, V> {

    public static final int MAX_ENTRIES = 5;

    private static final long serialVersionUID = 1L;
    private static final int INITIAL_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;
    private static final boolean ACCESS_ORDER = true;

    public SearchCache() {
        super(INITIAL_CAPACITY, LOAD_FACTOR, ACCESS_ORDER);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > MAX_ENTRIES;
    }
}
