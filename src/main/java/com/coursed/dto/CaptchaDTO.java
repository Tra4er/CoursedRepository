package com.coursed.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Trach on 1/5/2017.
 */
public abstract class CaptchaDTO {

//    @NotEmpty // TODO uncomment
    @JsonProperty("g-recaptcha-response")
    private String captchaResponse;

    public CaptchaDTO() {}

    public String getCaptchaResponse() {
        return captchaResponse;
    }

    public void setCaptchaResponse(String captchaResponse) {
        this.captchaResponse = captchaResponse;
    }

    @Override
    public String toString() {
        return "CaptchaDTO{" +
                "captchaResponse='" + captchaResponse + '\'' +
                '}';
    }
}
