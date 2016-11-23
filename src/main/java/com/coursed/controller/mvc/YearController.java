package com.coursed.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Hexray on 21.11.2016.
 */
@Controller
public class YearController {

    @RequestMapping(value = "/years", method = RequestMethod.GET)
    public String getPage()
    {
        return "years";
    }
}
