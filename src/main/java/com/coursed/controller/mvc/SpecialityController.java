package com.coursed.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Hexray on 01.12.2016.
 */
@Controller
public class SpecialityController {

    @RequestMapping(value = "/specialities", method = RequestMethod.GET)
    public String getPage()
    {
        return "specialities";
    }
}
