package com.hel.library.exception;

/** Thrown when a book with an already-registered ISBN is added. */
public class DuplicateIsbnException extends LibraryException {

    private static final long serialVersionUID = 1L;

    public DuplicateIsbnException(String message) {
        super(message);
    }

    public DuplicateIsbnException(String message, Throwable cause) {
        super(message, cause);
    }
}
