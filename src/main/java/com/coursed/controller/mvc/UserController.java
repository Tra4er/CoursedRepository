package com.coursed.controller.mvc;

import com.coursed.dto.RegistrationFormData;
import com.coursed.model.User;
import com.coursed.service.SecurityService;
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
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    // Login form
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public String login(Model model) {

        return "welcome";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(RegistrationFormData form, Model model) {
        if (form.getPassword().equals(form.getConfirmPassword())) {

            User user = new User();
            user.setEmail(form.getEmail());
            user.setPassword(form.getPassword());

            userService.save(user);
            securityService.autoLogin(user.getEmail(), user.getPassword());
        } else
            model.addAttribute("message", "Пароли не совпадают");

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
