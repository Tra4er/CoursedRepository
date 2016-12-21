package com.coursed.validator;

import com.coursed.dto.UserStudentDTO;
import com.coursed.dto.UserTeacherDTO;
import com.coursed.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.ValidationException;

/**
 * Created by Trach on 12/16/2016.
 */
@Component
public class UserTeacherRegistrationFormValidator implements Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserTeacherRegistrationFormValidator.class);

    @Autowired
    private UserService userService;

    public boolean supports(Class clazz) {
        return UserTeacherDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LOGGER.debug("Validating {}", target);
        UserTeacherDTO form = (UserTeacherDTO) target;
        try {
            System.out.println("HERE");
            BasicValidatorUtil.validateEmail(form, userService);
            BasicValidatorUtil.validatePasswords(form);
//            BasicValidatorUtil.validateNames(form);
//            validateNumber(form);
        } catch (ValidationException e) {
            System.out.println("Exception");
            errors.reject("error.user", e.getMessage());
            LOGGER.debug("Found invalid data for {}: {}", target, e.getMessage());
        }
    }

    private void validateNumber(UserTeacherDTO form) throws ValidationException {
        String reg = "^(\\+380)[0-9]{9}";
        if (!form.getPhoneNumber().matches(reg)) {
            throw new ValidationException("Wrong phone number");
        }
    }

}
