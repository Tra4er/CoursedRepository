package com.coursed.controllers.mvc;

import com.coursed.dto.RegistrationFormData;
import com.coursed.entities.User;
import com.coursed.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Created by Hexray on 16.10.2016.
 */

@Controller
public class UserController {

    @Autowired
    UserService userService;

    // Login form
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    // Login form with error
    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String getRegistration(Model model) {
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String postRegistration(RegistrationFormData form, Model model) {
        if (form.getPassword().equals(form.getConfirmPassword())) {

            User user = new User();
            user.setEmail(form.getEmail());
            user.setPassword(form.getPassword());

            userService.createUser(user);
        } else
            model.addAttribute("message", "Пароли не совпадают");

        return "registration";
    }

    @RequestMapping(value = "/user/{id}")
    public String viewAccount(@PathVariable("id") Long id, Model model) {

        return "accounts";
    }

    @RequestMapping(value = "/users")
    public String viewAllAccounts(Model model) {

        model.addAttribute("users", userService.findAll());

        return "users";
    }

}
