package com.coursed.validator;

import com.coursed.dto.BasicUserRegistrationForm;
import com.coursed.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

/**
 * Created by Trach on 12/16/2016.
 */
public class BasicValidatorUtil {

    @Autowired
    private static UserService userService;

    public static void validateEmail(BasicUserRegistrationForm form) throws Exception {
        String reg = "[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z]+(\\.[A-Za-z]+)*(\\.[A-Za-z]{2,5})$";
        if (!form.getEmail().matches(reg)) {
            throw new Exception("Wrong characters in email");
        }
        if (userService.getUserByEmail(form.getEmail()).isPresent()) {
            throw new Exception("Email exists");
        }
    }

    public static void validatePasswords(BasicUserRegistrationForm form) throws Exception{
        String reg = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";
        if(!form.getPassword().matches(reg)) {
            throw new Exception("Password is too simple");
        }
        if (!form.getPassword().equals(form.getConfirmPassword())) {
            throw new Exception("Passwords do not match");
        }
    }

    public static void validateNames(BasicUserRegistrationForm form) throws Exception {
        String reg = "^[А-Я][а-я]{1,15}";
        if (!form.getFirstName().matches(reg)) {
            throw new Exception("First name is wrong");
        }
        if (!form.getLastName().matches(reg)) {
            throw new Exception("Last name is wrong");
        }
        if (!form.getPatronymic().matches(reg)) {
            throw new Exception("Patronymic is wrong");
        }
    }
}
