package com.coursed.validator;

import com.coursed.dto.BasicPersonDTO;
import com.coursed.error.exception.UserAlreadyExistException;
import com.coursed.service.UserService;

import javax.validation.ValidationException;

/**
 * Created by Trach on 12/16/2016.
 */
public class BasicValidatorUtil {

    public static void validateEmail(BasicPersonDTO form, UserService userService) throws ValidationException, UserAlreadyExistException {
        String reg = "[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z]+(\\.[A-Za-z]+)*(\\.[A-Za-z]{2,5})$";
        if (!form.getEmail().matches(reg)) {
            throw new ValidationException("WrongCharactersInEmail");
        }
        if (userService.checkIfUserExists(form.getEmail())) {
            throw new UserAlreadyExistException("There is an account with this email.");
        }
    }

    public static void validatePasswords(BasicPersonDTO form) throws ValidationException {
        String reg = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";
        if(!form.getPassword().matches(reg)) {
            throw new ValidationException("PasswordIsTooSimple");
        }
        if (!form.getPassword().equals(form.getConfirmPassword())) {
            throw new ValidationException("PasswordsDoNotMatch");
        }
    }

    public static void validateNames(BasicPersonDTO form) throws ValidationException {
        String reg = "^[А-ЯІЄҐ][а-яієґ]{1,15}";
        if (!form.getFirstName().matches(reg)) {
            throw new ValidationException("FirstNameIsWrong");
        }
        if (!form.getLastName().matches(reg)) {
            throw new ValidationException("LastNameIsWrong");
        }
        if (!form.getPatronymic().matches(reg)) {
            throw new ValidationException("PatronymicIsWrong");
        }
    }

    public void validateNumber(BasicPersonDTO form) throws ValidationException {
        String reg = "^(\\+380)[0-9]{9}";
        if (!form.getPhoneNumber().matches(reg)) {
            throw new ValidationException("WrongPhoneNumber");
        }
    }
}
