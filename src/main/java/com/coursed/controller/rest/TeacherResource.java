package com.coursed.controller.rest;

import com.coursed.dto.UserTeacherDTO;
import com.coursed.error.exception.UserAlreadyExistException;
import com.coursed.model.auth.User;
import com.coursed.registration.OnRegistrationCompleteEvent;
import com.coursed.service.TeacherService;
import com.coursed.service.UserService;
import com.coursed.util.GenericResponse;
import com.coursed.validator.RecaptchaResponseDTOValidator;
import com.coursed.validator.UserTeacherDTOValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by Hexray on 17.12.2016.
 */
@RestController
@RequestMapping("/api/teachers")
public class TeacherResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherResource.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private UserTeacherDTOValidator userTeacherDTOValidator;

    @Autowired
    private RecaptchaResponseDTOValidator recaptchaResponseDTOValidator;

    @InitBinder("userTeacherDTO")
    public void initTeacherBinder(WebDataBinder binder) {
        binder.addValidators(recaptchaResponseDTOValidator, userTeacherDTOValidator);
    }

//    @PreAuthorize("hasAnyRole('HEAD', 'SECRETARY')")
    @GetMapping
    public ResponseEntity<GenericResponse> get(@RequestParam(name = "disciplineId", required = false) Long disciplineId,
                                               @RequestParam(name = "withDiscipline", required = false) Boolean withDiscipline) {
        if(disciplineId != null && withDiscipline) {
            return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                    teacherService.findAllTeachersWithDiscipline(disciplineId)), HttpStatus.OK);
        }
        if(disciplineId != null && !withDiscipline) {
            return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                    teacherService.findAllTeachersWithoutDiscipline(disciplineId)), HttpStatus.OK);
        }
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success", teacherService.findAll()),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GenericResponse> registerTeacherAccount(@Valid @RequestBody UserTeacherDTO userTeacherDTO,
                                                                     final HttpServletRequest request) {
        LOGGER.debug("Registering user account with information: {}", userTeacherDTO);

        User registered = userService.registerTeacher(userTeacherDTO);
        if (registered == null) {
            System.out.println("Exception");
            throw new UserAlreadyExistException();
        }

        final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), appUrl));

        return new ResponseEntity<>(new GenericResponse(HttpStatus.CREATED.value(), "success", registered.getId()),
                HttpStatus.CREATED);
    }

//    @PreAuthorize("hasAnyRole('HEAD', 'SECRETARY')")
    @GetMapping("{username}")
    public ResponseEntity<GenericResponse> getByUsername(@PathVariable("username") String username) {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                userService.getUserByEmail(username)), HttpStatus.OK);
    }

}
