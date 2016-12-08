package com.coursed.controller.rest;

import com.coursed.dto.StudentRegistrationForm;
import com.coursed.model.Student;
import com.coursed.model.auth.User;
import com.coursed.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.ResponseWrapper;

/**
 * Created by Trach on 11/24/2016.
 */
@RestController
@RequestMapping("/api/user")
public class UserResource {
    @Autowired
    private UserService userService;

    @GetMapping("/checkEmail")
    private boolean checkEmail(@RequestParam("email") String email) {
        return userService.getUserByEmail(email).isPresent();
    }

    @GetMapping("/getUser")
    private User getUser(@RequestParam("email") String email) {
        return userService.getUserByEmail(email).get();
    }

    @PostMapping("/createStudent")
    private void createStudent(@RequestBody StudentRegistrationForm studentRegistrationForm){

        Student student = new Student();
        student.setFirstName(studentRegistrationForm.getFirstName());
        student.setLastName(studentRegistrationForm.getLastName());
        student.setPatronymic(studentRegistrationForm.getPatronymic());

    }
}
