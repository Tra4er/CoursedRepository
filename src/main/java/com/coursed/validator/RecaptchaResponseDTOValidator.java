package com.coursed.validator;

import com.coursed.captcha.CaptchaService;
import com.coursed.dto.CaptchaDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


/**
 * Created by Trach on 1/5/2017.
 */
@Component
public class RecaptchaResponseDTOValidator implements Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecaptchaResponseDTOValidator.class);

    @Autowired
    private CaptchaService captchaService;

    @Override
    public boolean supports(Class<?> clazz) {
        return CaptchaDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LOGGER.debug("Validating {}", target);
        CaptchaDTO form = (CaptchaDTO) target;
//        captchaService.processResponse(form.getCaptchaResponse());
    }
}
