package com.coursed.captcha;

import com.coursed.error.exception.ReCaptchaInvalidException;

/**
 * Created by Trach on 1/3/2017.
 */
public interface CaptchaService {
    void processResponse(final String response) throws ReCaptchaInvalidException;

    String getReCaptchaSite();

    String getReCaptchaSecret();
}
