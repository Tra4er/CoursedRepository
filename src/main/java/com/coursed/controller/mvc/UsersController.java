package com.coursed.controller.mvc;

import com.coursed.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Trach on 11/9/2016.
 */
@Controller
public class UsersController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users")
    public String viewAllUsers(Model model) {

        model.addAttribute("users", userService.findAll());

        return "users";
    }
}
