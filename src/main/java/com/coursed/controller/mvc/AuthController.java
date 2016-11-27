package com.coursed.controller.mvc;

import com.coursed.dto.UserRegistrationForm;
import com.coursed.model.auth.User;
import com.coursed.service.SecurityService;
import com.coursed.service.UserService;
import com.coursed.validator.UserRegistrationFormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

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
        LOGGER.debug("Sending userForm to client");
        return new ModelAndView("auth/registration", "userForm", new UserRegistrationForm());
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@Valid @ModelAttribute("userForm") UserRegistrationForm userForm, BindingResult bindingResult) {

        LOGGER.debug("Processing user registration userForm={}, bindingResult={}", userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "auth/registration";
        }

        User user = new User();
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());

        try{
            userService.register(user);
        } catch(DataIntegrityViolationException e) { // TODO Not sure about this
            LOGGER.warn("Exception occurred when trying to save the user, assuming duplicate email", e);
            bindingResult.rejectValue("email", "error.user", "Email already exists");
            return "auth/registration";
        }

        securityService.autoLogin(userForm.getEmail(), userForm.getPassword());

        return "redirect:/";
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView getLoginPage(@RequestParam Optional<String> error) {
        LOGGER.debug("Getting login page, error={}", error);
        return new ModelAndView("auth/login", "error", error);
    }


}
