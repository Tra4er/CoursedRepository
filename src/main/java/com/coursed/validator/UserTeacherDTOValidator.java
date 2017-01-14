package com.coursed.validator;

import com.coursed.dto.UserTeacherDTO;
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
public class UserTeacherDTOValidator implements Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserTeacherDTOValidator.class);

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class clazz) {
        return UserTeacherDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LOGGER.debug("Validating {}", target);
        UserTeacherDTO form = (UserTeacherDTO) target;
        BasicPersonValidatorUtil.validateEmail(form, userService);
//        BasicPersonValidatorUtil.validatePasswords(form);
//      BasicPersonValidatorUtil.validateNames(form);
//      BasicPersonValidatorUtil.validateNumber(form);
    }

}
