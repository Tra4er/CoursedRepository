package com.coursed.controller.rest;

import com.coursed.controller.mvc.AuthController;
import com.coursed.dto.StudentRegistrationForm;
import com.coursed.dto.UserRegistrationForm;
import com.coursed.model.Student;
import com.coursed.model.auth.User;
import com.coursed.registration.OnRegistrationCompleteEvent;
import com.coursed.security.SecurityService;
import com.coursed.service.UserService;
import com.coursed.validator.UserRegistrationFormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.ws.ResponseWrapper;

/**
 * Created by Trach on 11/24/2016.
 */
@RestController
@RequestMapping("/api/user")
public class UserResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

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
