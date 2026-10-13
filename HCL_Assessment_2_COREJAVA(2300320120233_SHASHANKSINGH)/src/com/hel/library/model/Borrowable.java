package com.hel.library.model;

/** Something a member can take out of the library and later give back. */
public interface Borrowable {

    boolean isAvailable();

    void borrow();

    void giveback();
}
