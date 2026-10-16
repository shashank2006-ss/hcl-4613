package com.hel.library.exception;

/** Thrown when a member id is not registered. */
public class MemberNotFoundException extends LibraryException {

    private static final long serialVersionUID = 1L;

    public MemberNotFoundException(String message) {
        super(message);
    }

    public MemberNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
