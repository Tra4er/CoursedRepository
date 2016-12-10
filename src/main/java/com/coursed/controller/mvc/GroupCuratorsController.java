package com.coursed.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by alena on 10.12.2016.
 */
@Controller
public class GroupCuratorsController {

    @GetMapping("/groupCurators")
    public String getPage()
    {
        return "groupCurators";
    }
}