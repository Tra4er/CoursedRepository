package com.coursed.controller.mvc;

import com.coursed.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Trach on 11/9/2016.
 */
@Controller
public class UsersController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String viewAllUsers(Model model) {

        LOGGER.debug("Getting users page");

        model.addAttribute("users", userService.findAll());

        return "users";
    }
}
