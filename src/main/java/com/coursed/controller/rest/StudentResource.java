package com.coursed.controller.rest;

import com.coursed.dto.UserStudentDTO;
import com.coursed.error.exception.UserAlreadyExistException;
import com.coursed.model.auth.User;
import com.coursed.registration.OnRegistrationCompleteEvent;
import com.coursed.service.*;
import com.coursed.util.GenericResponse;
import com.coursed.validator.RecaptchaResponseDTOValidator;
import com.coursed.validator.UserStudentDTOValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by Trach on 12/13/2016.
 */
@RestController
@RequestMapping("/api/students")
public class StudentResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentResource.class);

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private UserStudentDTOValidator userStudentDTOValidator;


    @Autowired
    private RecaptchaResponseDTOValidator recaptchaResponseDTOValidator;

    @InitBinder("userStudentDTO")
    public void initStudentBinder(WebDataBinder binder) {
        binder.addValidators(recaptchaResponseDTOValidator, userStudentDTOValidator);
    }

//    @PreAuthorize("hasAnyRole('HEAD','TEACHER', 'SECRETARY')")
    @GetMapping
    public ResponseEntity<GenericResponse> get(@RequestParam(value = "page", required = false) Integer page,
                                               @RequestParam(value = "size", required = false) Integer size) {

        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success", studentService.getAll()),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GenericResponse> registerStudentAccount(@Valid @RequestBody UserStudentDTO userStudentDTO,
                                                                  final HttpServletRequest request) {
        LOGGER.debug("Registering user account with information: {}", userStudentDTO);

        User registered = userService.registerStudent(userStudentDTO);
        if (registered == null) {
            throw new UserAlreadyExistException();
        }

        final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), appUrl));

        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success", registered.getId()),
                HttpStatus.CREATED);
    }

    //    @PreAuthorize("hasAnyRole('HEAD','TEACHER', 'SECRETARY')")
    @GetMapping("/search")
    public ResponseEntity<GenericResponse> search(@RequestParam(value = "page", required = false) Integer page,
                                               @RequestParam(value = "size", required = false) Integer size,
                                               @RequestParam(value = "groupId", required = false) Long groupId) {
        if(groupId != null) {
            return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                    studentService.getAllFromGroup(groupId)), HttpStatus.OK);
        }
        return new ResponseEntity<>(new GenericResponse(HttpStatus.NO_CONTENT.value(), "success"), HttpStatus.NO_CONTENT);
    }

    //    @PreAuthorize("hasAnyRole('HEAD','TEACHER', 'SECRETARY')")
    @GetMapping("{id}")
    public ResponseEntity<GenericResponse> getByUsername(@PathVariable("id") Long id) {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                studentService.getById(id)), HttpStatus.OK);
    }

}
