package com.coursed.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Hexray on 10.12.2016.
 */
@Controller
public class TeacherConfirmController {
    @RequestMapping("/teacherConfirm")
    public String getPage()
    {
        return "teacherConfirm";
    }
}
