package com.coursed.error.exception;

/**
 * Created by Trach on 1/10/2017.
 */
public class RegistrationException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366287163L;

    public RegistrationException() {
        super();
    }

    public RegistrationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public RegistrationException(final String message) {
        super(message);
    }

    public RegistrationException(final Throwable cause) {
        super(cause);
    }
}
