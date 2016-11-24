package com.coursed.controller.rest;

import com.coursed.model.Year;
import com.coursed.service.UserService;
import com.coursed.service.YearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Created by Trach on 11/24/2016.
 */
@RestController
@RequestMapping("/api/years")
public class UserResource {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/checkEmail", method = RequestMethod.POST)
    private boolean checkEmail(@RequestParam("email") String email) {
        if(userService.getUserByEmail(email).isPresent()) {
            return true;
        }
        return false;
    }
}
