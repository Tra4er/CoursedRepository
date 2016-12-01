package com.coursed.controller.rest;

import com.coursed.model.auth.User;
import com.coursed.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.ResponseWrapper;

/**
 * Created by Trach on 11/24/2016.
 */
@RestController
@RequestMapping
public class UserResource {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/api/user/checkEmail", method = RequestMethod.GET)
    private boolean checkEmail(@RequestParam("email") String email) {
        return userService.getUserByEmail(email).isPresent();
    }

    @RequestMapping("/api/user/getUser")
    public User getUser(@RequestParam("email") String email) {
        return userService.getUserByEmail(email).get();
    }
}
