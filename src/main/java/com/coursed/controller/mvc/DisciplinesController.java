package com.coursed.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by alena on 15.12.2016.
 */

@Controller
public class DisciplinesController {

    @GetMapping("/disciplines")
    public String getPage()
    {
        return "disciplines";
    }
}
