package com.coursed.controller.mvc;

import com.coursed.dto.UserRegistrationForm;
import com.coursed.service.SecurityService;
import com.coursed.service.UserService;
import com.coursed.validator.UserRegistrationFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Created by Hexray on 13.11.2016.
 */
@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRegistrationFormValidator userRegistrationFormValidator;

    @Autowired
    private SecurityService securityService;

    @InitBinder("userForm")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userRegistrationFormValidator);
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        return new ModelAndView("auth/registration", "userForm", new UserRegistrationForm());
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@Valid @ModelAttribute("userForm") UserRegistrationForm userForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            System.out.println("Field Error: " + bindingResult.getFieldError());
            System.out.println("Error Counter: " + bindingResult.getErrorCount());
            System.out.println("Model: " + bindingResult.getModel());
            System.out.println("All Errors: " + bindingResult.getAllErrors());
            return "auth/registration";
        }

        try{
        userService.save(userForm);
        } catch(DataIntegrityViolationException e) {
            bindingResult.reject("email", "Email already exists");
            return "auth/registration";
        }

        securityService.autoLogin(userForm.getEmail(), userForm.getPassword());

        return "redirect:/";
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView getLoginPage(@RequestParam Optional<String> error) {
        return new ModelAndView("auth/login", "error", error);
    }


}
