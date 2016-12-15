package com.coursed.validator;

import com.coursed.dto.UserStudentRegistrationForm;
import com.coursed.dto.UserTeacherRegistrationForm;
import com.coursed.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Trach on 12/16/2016.
 */
@Component
public class UserTeacherRegistrationFormValidator implements Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserTeacherRegistrationFormValidator.class);

    @Autowired
    private UserService userService;

    public boolean supports(Class clazz) {
        return UserTeacherRegistrationForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LOGGER.debug("Validating {}", target);
        UserTeacherRegistrationForm form = (UserTeacherRegistrationForm) target;
        validatePasswords(errors, form);
        validateEmail(errors, form);
    }

    private void validatePasswords(Errors errors, UserTeacherRegistrationForm form) {
        if (!form.getPassword().equals(form.getConfirmPassword())) {
            errors.rejectValue("password", "error.user", "Passwords do not match");
        }
    }

    private void validateEmail(Errors errors, UserTeacherRegistrationForm form) {
        if (userService.getUserByEmail(form.getEmail()).isPresent()) {
            errors.rejectValue("email", "error.user","Email exists");
        }
    }
}
