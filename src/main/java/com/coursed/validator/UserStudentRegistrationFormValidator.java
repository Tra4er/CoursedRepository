package com.coursed.validator;

import com.coursed.dto.UserStudentRegistrationForm;
import com.coursed.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Trach on 12/15/2016.
 */
@Component
public class UserStudentRegistrationFormValidator implements Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserStudentRegistrationFormValidator.class);

    @Autowired
    private UserService userService;

    public boolean supports(Class clazz) {
        return UserStudentRegistrationForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LOGGER.debug("Validating {}", target);
        UserStudentRegistrationForm form = (UserStudentRegistrationForm) target;
        validatePasswords(errors, form);
        validateEmail(errors, form);
    }

    private void validatePasswords(Errors errors, UserStudentRegistrationForm form) {
        if (!form.getPassword().equals(form.getConfirmPassword())) {
            errors.rejectValue("password", "error.user", "Passwords do not match");
        }
    }

    private void validateEmail(Errors errors, UserStudentRegistrationForm form) {
        if (userService.getUserByEmail(form.getEmail()).isPresent()) {
            errors.rejectValue("email", "error.user","Email exists");
        }
    }
}
