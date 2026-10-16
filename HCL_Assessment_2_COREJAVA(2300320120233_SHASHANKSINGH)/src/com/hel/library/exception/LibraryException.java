package com.hel.library.exception;

/** Base type for every business-rule failure in the library system. */
public class LibraryException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public LibraryException(String message) {
        super(message);
    }

    public LibraryException(String message, Throwable cause) {
        super(message, cause);
    }
}
