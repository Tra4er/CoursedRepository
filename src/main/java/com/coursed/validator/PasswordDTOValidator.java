package com.coursed.validator;

import com.coursed.dto.PasswordDTO;
import com.coursed.model.auth.User;
import com.coursed.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.ValidationException;

/**
 * Created by Trach on 12/30/2016.
 */
@Component
public class PasswordDTOValidator implements Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordDTOValidator.class);

    @Autowired
    private UserService userService;

    public boolean supports(Class clazz) {
        return PasswordDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LOGGER.debug("Validating {}", target);
        PasswordDTO form = (PasswordDTO) target;
//        validatePasswords(form); // TODO uncomment
    }

    private void validatePasswords(PasswordDTO form) {
        String reg = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";
        if (!form.getNewPassword().matches(reg)) {
            throw new ValidationException("PasswordIsTooSimple");
        }
        if (!form.getNewPassword().equals(form.getConfirmPassword())) {
            throw new ValidationException("PasswordsDoNotMatch");
        }
    }
}
