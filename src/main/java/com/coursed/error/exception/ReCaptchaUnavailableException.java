package com.coursed.error.exception;

/**
 * Created by Trach on 1/3/2017.
 */
public class ReCaptchaUnavailableException extends RuntimeException {

    private static final long serialVersionUID = -3314955148586174072L;

    public ReCaptchaUnavailableException() {
        super();
    }

    public ReCaptchaUnavailableException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ReCaptchaUnavailableException(final String message) {
        super(message);
    }

    public ReCaptchaUnavailableException(final Throwable cause) {
        super(cause);
    }
}
