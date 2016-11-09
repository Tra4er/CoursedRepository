package com.coursed.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Trach on 11/9/2016.
 */
@Controller
public class HomeController {

    @RequestMapping("/")
    public String getHomePage() {
        return "welcome";
    }
}
