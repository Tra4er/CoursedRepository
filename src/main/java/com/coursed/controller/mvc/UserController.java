package com.coursed.controller.mvc;

import com.coursed.dto.UserRegistrationForm;
import com.coursed.service.SecurityService;
import com.coursed.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;


/**
 * Created by Hexray on 16.10.2016.
 */

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    // Login form
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView getLoginPage(@RequestParam Optional<String> error) {
        return new ModelAndView("login", "error", error);
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

    @RequestMapping(value = "/user/{id}")
    public String viewUser(@PathVariable("id") Long id, Model model) {

        return "user";
    }

    @RequestMapping(value = "/users")
    public String viewAllUsers(Model model) {

        model.addAttribute("users", userService.findAll());

        return "users";
    }

}
