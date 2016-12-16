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
        BasicValidatorUtil basicValidatorUtil = new BasicValidatorUtil();
        UserTeacherRegistrationForm form = (UserTeacherRegistrationForm) target;
        try {
            basicValidatorUtil.validateEmail(form);
            basicValidatorUtil.validatePasswords(form);
            basicValidatorUtil.validateNames(form);
        } catch (Exception e) {
            errors.reject("error.user", e.getMessage());
            LOGGER.debug("Found invalid data for {}: {}", target, e.getMessage());
        }
    }

}
