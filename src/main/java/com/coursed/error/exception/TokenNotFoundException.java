package com.coursed.error.exception;

/**
 * Created by Trach on 1/3/2017.
 */
public class TokenNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 5861310537356687163L;

    public TokenNotFoundException() {
        super();
    }

    public TokenNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public TokenNotFoundException(final String message) {
        super(message);
    }

    public TokenNotFoundException(final Throwable cause) {
        super(cause);
    }
}
