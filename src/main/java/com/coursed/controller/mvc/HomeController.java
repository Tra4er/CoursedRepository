package com.coursed.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

/**
 * Created by Trach on 11/9/2016.
 */
@Controller
public class HomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getLoginPage(Model model, Principal principal) {

        model.addAttribute("currentUser", principal.getName());
        int a = 5;
        return "welcome";
    }
}
