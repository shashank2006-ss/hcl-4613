package com.hel.library.exception;

/** Thrown when a member tries to hold more items than the borrowing limit allows. */
public class BorrowLimitExceededException extends LibraryException {

    private static final long serialVersionUID = 1L;

    public BorrowLimitExceededException(String message) {
        super(message);
    }

    public BorrowLimitExceededException(String message, Throwable cause) {
        super(message, cause);
    }
}
