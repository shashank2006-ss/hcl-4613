package com.hel.library.repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/** Minimal storage abstraction for entities of type {@code T} identified by {@code ID}. */
public interface Repository<T, ID> {

    void save(T entity);

    Optional<T> findById(ID id);

    /** Returns an unmodifiable snapshot of every stored entity. */
    List<T> findAll();

    boolean deleteById(ID id);

    /** Removes every entity matching the filter and returns how many were removed. */
    int removeIf(Predicate<T> filter);
}
