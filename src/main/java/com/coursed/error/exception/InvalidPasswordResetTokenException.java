package com.coursed.error.exception;

/**
 * Created by Trach on 12/31/2016.
 */
public class InvalidPasswordResetTokenException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366287163L;

    public InvalidPasswordResetTokenException() {
        super();
    }

    public InvalidPasswordResetTokenException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InvalidPasswordResetTokenException(final String message) {
        super(message);
    }

    public InvalidPasswordResetTokenException(final Throwable cause) {
        super(cause);
    }
}
