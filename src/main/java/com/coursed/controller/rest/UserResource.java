package com.coursed.controller.rest;

import com.coursed.controller.mvc.AuthController;
import com.coursed.dto.*;
import com.coursed.model.Student;
import com.coursed.model.Teacher;
import com.coursed.model.auth.User;
import com.coursed.model.auth.VerificationToken;
import com.coursed.registration.OnRegistrationCompleteEvent;
import com.coursed.security.SecurityService;
import com.coursed.service.TeacherService;
import com.coursed.service.UserService;
import com.coursed.validator.UserRegistrationFormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.ws.ResponseWrapper;
import java.util.Calendar;
import java.util.Collection;

/**
 * Created by Trach on 11/24/2016.
 */
@RestController
@RequestMapping("/api/user")
public class UserResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private UserRegistrationFormValidator userRegistrationFormValidator;

    @InitBinder("userForm")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userRegistrationFormValidator);
    }

    @GetMapping("/checkEmail")
    private boolean checkEmail(@RequestParam("email") String email) {
        return userService.getUserByEmail(email).isPresent();
    }

    @GetMapping("/getUser")
    private User getUser(@RequestParam("email") String email) {
        return userService.getUserByEmail(email).get();
    }
//    @PostMapping("/registrations")
//    public String registration(@Valid @ModelAttribute("userForm") UserRegistrationForm userForm,
//                               @RequestBody(required = false) StudentRegistrationForm studentForm,
//                               @RequestBody(required = false) TeacherRegistrationForm teacherForm,
//                               BindingResult bindingResult, final HttpServletRequest request) {
//
//        LOGGER.debug("Processing user registration userForm={}, bindingResult={}", userForm, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            return "auth/registration";
//        }
//
//        if(studentForm == null && teacherForm == null)
//            throw new RuntimeException("No entity except User has been found in registration request");
//
//        User registered;
//        try{
//            if(studentForm != null)
//                registered = userService.registerStudent(userForm, studentForm);
//            else
//                registered = userService.registerTeacher(userForm, teacherForm);
//        } catch(DataIntegrityViolationException e) {
//            LOGGER.warn("Exception occurred when trying to save the user, assuming duplicate email", e);
//            return "auth/registration";
//        }
//
//        try {
//            final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
//            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), appUrl));
//        } catch (final Exception ex) {
//            LOGGER.warn("Unable to register user", ex);
//        }
//
//        securityService.autoLogin(userForm.getEmail(), userForm.getPassword()); // TODO read about
//
//        return "/auth/verifyYourAccount";
//    }
//TODO ADD VALIDATION
    @PostMapping("/registration-student")
    public String regStudent(@RequestBody UserStudentRegistrationForm userStudentRegistrationForm, final HttpServletRequest request) {

        //LOGGER.debug("Processing user registration userForm={}, bindingResult={}", userForm, bindingResult);

//        if (bindingResult.hasErrors()) {
//            return "auth/registration";
//        }

        User registered;
        try{
            registered = userService.registerStudent(userStudentRegistrationForm);
        } catch(DataIntegrityViolationException e) {
            LOGGER.warn("Exception occurred when trying to save the user, assuming duplicate email", e);
            return "auth/registration";
        }

        try {
            final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), appUrl));
        } catch (final Exception ex) {
            LOGGER.warn("Unable to register user", ex);
        }

        //securityService.autoLogin(userForm.getEmail(), userForm.getPassword()); // TODO read about

        return "/auth/verifyYourAccount";
    }

    @PostMapping("/registration-teacher")
    public String regTeacher(@RequestBody UserTeacherRegistrationForm userTeacherRegistrationForm, final HttpServletRequest request) {

        //LOGGER.debug("Processing user registration userForm={}, bindingResult={}", userForm, bindingResult);

//        if (bindingResult.hasErrors()) {
//            return "auth/registration";
//        }

        User registered;
        try{
            registered = userService.registerTeacher(userTeacherRegistrationForm);
        } catch(DataIntegrityViolationException e) {
            LOGGER.warn("Exception occurred when trying to save the user, assuming duplicate email", e);
            return "auth/registration";
        }

        try {
            final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), appUrl));
        } catch (final Exception ex) {
            LOGGER.warn("Unable to register user", ex);
        }

        //securityService.autoLogin(userForm.getEmail(), userForm.getPassword()); // TODO read about

        return "/auth/verifyYourAccount";
    }

    @GetMapping("/registrationConfirm")
    public String confirmRegistration(Model model, @RequestParam("token") String token, RedirectAttributes redAtt) {
        LOGGER.debug("Receiving confirmation token: {}", token);

        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) { // TODO
            LOGGER.debug("Invalid token received: {}", token);
            String message = "Invalid token received: " + token;
            model.addAttribute("message", message);
            return "/auth/badUser";
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            LOGGER.debug("Verification token expired for user: {}", user);
            String messageValue = "Verification token expired for user: " + user.getEmail();
            model.addAttribute("message", messageValue);
            return "/auth/badUser";
        }

        user.setEnabled(true);
        userService.saveRegisteredUser(user);
        LOGGER.debug("Received verification from user: {}", user);
        redAtt.addFlashAttribute("message", "Ви активували свій акаунт. Увійдіть.");
        return "redirect:/login";
    }

    @PostMapping("/confirm-teacher")
    public void confirmTeacher(@RequestParam(name = "userId") Long userId){
        userService.makeATeacher(userId);
    }

    @GetMapping("/getAllUnconfirmedTeachers")
    Collection<User> getAllUnconfirmedTeachers(){
        return userService.findAllUnconfirmedTeachers();
    }

    @GetMapping("/getAllTeachers")
    Collection<User> getAllTeachers(){
        return userService.findAllTeachers();
    }
}
