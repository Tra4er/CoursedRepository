package com.coursed.validator;

import com.coursed.dto.UserRegistrationForm;
import com.coursed.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Trach on 11/8/2016.
 */
@Component
public class UserRegistrationFormValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UserRegistrationForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRegistrationForm form = (UserRegistrationForm) target;
        validatePasswords(errors, form);
        validateEmail(errors, form);
    }

    private void validatePasswords(Errors errors, UserRegistrationForm form) {
        if (!form.getPassword().equals(form.getConfirmPassword())) {
            errors.reject("password.no_match", "Passwords do not match");
        }
    }

    private void validateEmail(Errors errors, UserRegistrationForm form) {
        if (userService.getUserByEmail(form.getEmail()).isPresent()) {
            errors.reject("email.exists", "User with this email already exists");
        }
    }
}
