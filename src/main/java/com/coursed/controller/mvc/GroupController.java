package com.coursed.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Hexray on 04.12.2016.
 */
@Controller
public class GroupController {

    @GetMapping("/groups")
    public String getPage()
    {
        return "groups";
    }
}