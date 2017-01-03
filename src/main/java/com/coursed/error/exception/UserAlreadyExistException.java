package com.coursed.error.exception;

/**
 * Created by Trach on 12/20/2016.
 */
public class UserAlreadyExistException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366287163L; // TODO

    public UserAlreadyExistException() {
        super();
    }

    public UserAlreadyExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyExistException(final String message) {
        super(message);
    }

    public UserAlreadyExistException(final Throwable cause) {
        super(cause);
    }
}
