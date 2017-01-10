package com.coursed.error.exception;

/**
 * Created by Trach on 12/22/2016.
 */
public final class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -3805783614840050848L;

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(final String message) {
        super(message);
    }

    public UserNotFoundException(final Throwable cause) {
        super(cause);
    }

}
