package com.coursed.error.exception;

/**
 * Created by Trach on 1/29/2017.
 */
public class PageSizeTooBigException extends RuntimeException {

    private static final long serialVersionUID = 5205719343616646638L;

    public PageSizeTooBigException() {
        super();
    }

    public PageSizeTooBigException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PageSizeTooBigException(final String message) {
        super(message);
    }

    public PageSizeTooBigException(final Throwable cause) {
        super(cause);
    }
}
