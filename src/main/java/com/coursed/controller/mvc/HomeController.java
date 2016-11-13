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

        if(principal == null)
        {
            return "redirect:/login";
        }

        model.addAttribute("currentUser", principal.getName());
        return "welcome";
    }
}
