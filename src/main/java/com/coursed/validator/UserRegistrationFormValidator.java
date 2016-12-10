package com.coursed.validator;

import com.coursed.dto.UserRegistrationForm;
import com.coursed.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Trach on 11/16/2016.
 */
@Component
public class UserRegistrationFormValidator implements Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRegistrationFormValidator.class);

    @Autowired
    private UserService userService;

    public boolean supports(Class clazz) {
        return UserRegistrationForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LOGGER.debug("Validating {}", target);
        UserRegistrationForm form = (UserRegistrationForm) target;
        validatePasswords(errors, form);
        validateEmail(errors, form);
    }

    private void validatePasswords(Errors errors, UserRegistrationForm form) {
        if (!form.getPassword().equals(form.getConfirmPassword())) {
            errors.rejectValue("password", "error.user", "Passwords do not match");
        }
    }

    private void validateEmail(Errors errors, UserRegistrationForm form) {
        if (userService.getUserByEmail(form.getEmail()).isPresent()) {
            errors.rejectValue("email", "error.user","Email exists");
        }
    }
}
