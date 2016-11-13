package com.coursed.controller.mvc;

import com.coursed.dto.UserRegistrationForm;
import com.coursed.service.SecurityService;
import com.coursed.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by Hexray on 13.11.2016.
 */
@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration() {
        return "auth/registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("form") UserRegistrationForm userForm) {

        try{
        userService.save(userForm);
        } catch(DataIntegrityViolationException e) {
            return "error";
        }

        securityService.autoLogin(userForm.getEmail(), userForm.getPassword());

        return "redirect:/";
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage() {
        return "auth/login";
    }


}
