package com.coursed.controllers.rest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Hexray on 06.11.2016.
 */
@RestController
public class RestUserController {

    @RequestMapping(value = "/user/setRole/{id}")
    public String setStudentRole(@PathVariable("id") Long id){
        return "text";
    }
}
