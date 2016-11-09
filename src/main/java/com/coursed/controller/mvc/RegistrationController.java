package com.coursed.controller.mvc;

import com.coursed.dto.UserRegistrationForm;
import com.coursed.service.SecurityService;
import com.coursed.service.UserService;
import com.coursed.validator.UserRegistrationFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;


/**
 * Created by Hexray on 16.10.2016.
 */

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRegistrationFormValidator userRegistrationFormValidator;

    @Autowired
    private SecurityService securityService;

    @InitBinder("form")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userRegistrationFormValidator);
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        return new ModelAndView("registration", "form", new UserRegistrationForm());
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@Valid @ModelAttribute("form") UserRegistrationForm userForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user_create";
        }

        try{
            userService.save(userForm);
        } catch(DataIntegrityViolationException e) {
            bindingResult.reject("email.exists", "Email already exists");
            return "registration";
        }

        securityService.autoLogin(userForm.getEmail(), userForm.getPassword());

        return "redirect:/";
    }
}
