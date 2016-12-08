package com.coursed.controller.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

/**
 * Created by Trach on 11/9/2016.
 */
@Controller
public class MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    @GetMapping("/")
    public String getMainPage(Model model, Principal principal) {
        LOGGER.debug("Send homepage to currentUser={}", principal.getName());

        model.addAttribute("currentUser", principal.getName());
        return "main";
    }
}
