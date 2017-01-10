package com.coursed.error.exception;

/**
 * Created by Trach on 12/30/2016.
 */
public class InvalidOldPasswordException extends RuntimeException {

    private static final long serialVersionUID = -913524261579333065L;

    public InvalidOldPasswordException() {
        super();
    }

    public InvalidOldPasswordException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InvalidOldPasswordException(final String message) {
        super(message);
    }

    public InvalidOldPasswordException(final Throwable cause) {
        super(cause);
    }
}
