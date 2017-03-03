package com.coursed.error.exception;

/**
 * Created by Trach on 3/3/2017.
 */
public class CaptchaException extends RuntimeException {

    private static final long serialVersionUID = 5122113364453104998L;

    public CaptchaException() {
        super();
    }

    public CaptchaException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CaptchaException(final String message) {
        super(message);
    }

    public CaptchaException(final Throwable cause) {
        super(cause);
    }

}
