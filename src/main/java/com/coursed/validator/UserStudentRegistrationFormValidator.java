package com.coursed.validator;

import com.coursed.dto.UserStudentDTO;
import com.coursed.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.ValidationException;

/**
 * Created by Trach on 12/15/2016.
 */
@Component
public class UserStudentRegistrationFormValidator implements Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserStudentRegistrationFormValidator.class);

    @Autowired
    private UserService userService;

    public boolean supports(Class clazz) {
        return UserStudentDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LOGGER.debug("Validating {}", target);
        UserStudentDTO form = (UserStudentDTO) target;
        try {
//            BasicValidatorUtil.validateEmail(form, userService);
//            BasicValidatorUtil.validatePasswords(form);
//            BasicValidatorUtil.validateNames(form);
//            validateNumber(form);
//            validateAddress(form);
//            validateSemester(form);
        } catch (ValidationException e) {
            errors.reject("error.user", e.getMessage());
            LOGGER.debug("Found invalid data for {}: {}", target, e.getMessage());
        }
    }

    private void validateNumber(UserStudentDTO form) throws ValidationException {
        String reg = "^(\\+380)[0-9]{9}";
        if (!form.getPhoneNumber().matches(reg)) {
            throw new ValidationException("Wrong phone number");
        }
    }

    private void validateAddress(UserStudentDTO form) throws ValidationException {
        String reg = "^(м\\.)\\s^[А-Я][а-я]{1,40}"; // TODO
//        if (!form.getPhoneNumber().matches(reg)) {
//            throw new Exception("Wrong phone number");
//        }
    }

    private void validateSemester(UserStudentDTO form) throws ValidationException {
        String reg = "FIRST|SECOND"; // TODO uncomment me when you will add phone number to model
//        if (!form.getSemester().matches(reg)) {
//            throw new Exception("Wrong semester number");
//        }
    }

}
