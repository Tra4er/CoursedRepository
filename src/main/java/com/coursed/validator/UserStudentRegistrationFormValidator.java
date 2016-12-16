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
        BasicValidatorUtil basicValidatorUtil = new BasicValidatorUtil();
        UserStudentRegistrationForm form = (UserStudentRegistrationForm) target;
        try {
            basicValidatorUtil.validateEmail(errors, form);
            basicValidatorUtil.validatePasswords(errors, form);
            basicValidatorUtil.validateNames(errors, form);
        } catch (Exception e) {
            errors.reject("error.user", e.getMessage());
        }
    }

}
