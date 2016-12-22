package com.coursed.validator;

import com.coursed.dto.UserTeacherDTO;
import com.coursed.security.error.UserAlreadyExistException;
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
public class UserTeacherDTOValidator implements Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserTeacherDTOValidator.class);

    @Autowired
    private UserService userService;

    public boolean supports(Class clazz) {
        return UserTeacherDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LOGGER.debug("Validating {}", target);
        UserTeacherDTO form = (UserTeacherDTO) target;
        BasicValidatorUtil.validateEmail(form, userService);
//        BasicValidatorUtil.validatePasswords(form);
//      BasicValidatorUtil.validateNames(form);
//      validateNumber(form);
    }

    private void validateNumber(UserTeacherDTO form) throws ValidationException {
        String reg = "^(\\+380)[0-9]{9}";
        if (!form.getPhoneNumber().matches(reg)) {
            throw new ValidationException("WrongPhoneNumber");
        }
    }

}