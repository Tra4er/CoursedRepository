package com.coursed.controller.rest;

import com.coursed.captcha.CaptchaService;
import com.coursed.dto.UserTeacherDTO;
import com.coursed.error.exception.UserAlreadyExistException;
import com.coursed.model.Teacher;
import com.coursed.model.auth.User;
import com.coursed.registration.OnRegistrationCompleteEvent;
import com.coursed.security.SecurityService;
import com.coursed.service.PasswordResetTokenService;
import com.coursed.service.TeacherService;
import com.coursed.service.UserService;
import com.coursed.service.VerificationTokenService;
import com.coursed.util.GenericResponse;
import com.coursed.util.OldGenericResponse;
import com.coursed.validator.PasswordDTOValidator;
import com.coursed.validator.RecaptchaResponseDTOValidator;
import com.coursed.validator.UserStudentDTOValidator;
import com.coursed.validator.UserTeacherDTOValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collection;

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
    private SecurityService securityService;

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    @Autowired
    private UserStudentDTOValidator userStudentDTOValidator;

    @Autowired
    private UserTeacherDTOValidator userTeacherDTOValidator;

    @Autowired
    private PasswordDTOValidator passwordDTOValidator;

    @Autowired
    private RecaptchaResponseDTOValidator recaptchaResponseDTOValidator;

    @InitBinder("userTeacherDTO")
    public void initTeacherBinder(WebDataBinder binder) {
        binder.addValidators(recaptchaResponseDTOValidator, userTeacherDTOValidator);
    }

    @PostMapping
    @ResponseBody
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

        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success", registered.getId()), HttpStatus.CREATED);
    }

    @GetMapping("/getAllWithDiscipline")
    Collection<Teacher> getAllTeachersWithDiscipline(@RequestParam(name = "disciplineId") Long disciplineId){
        return teacherService.findAllTeachersWithDiscipline(disciplineId);
    }

    @GetMapping("/getAllWithoutDiscipline")
    Collection<Teacher> getAllTeachersWithoutDiscipline(@RequestParam(name = "disciplineId") Long disciplineId){
        return teacherService.findAllTeachersWithoutDiscipline(disciplineId);
    }
}
