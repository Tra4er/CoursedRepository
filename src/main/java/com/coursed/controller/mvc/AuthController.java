package com.coursed.controller.mvc;

import com.coursed.dto.*;
import com.coursed.model.auth.User;
import com.coursed.model.auth.VerificationToken;
import com.coursed.registration.OnRegistrationCompleteEvent;
import com.coursed.security.LoginAttemptService;
import com.coursed.security.SecurityService;
import com.coursed.service.UserService;
import com.coursed.validator.UserStudentDTOValidator;
import com.coursed.validator.UserTeacherDTOValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Calendar;

/**
 * Created by Hexray on 13.11.2016.
 */
@Controller
public class AuthController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private UserStudentDTOValidator userStudentDTOValidator;

    @Autowired
    private UserTeacherDTOValidator userTeacherDTOValidator;

    @InitBinder("studentForm")
    public void initStudentBinder(WebDataBinder binder) {
        binder.addValidators(userStudentDTOValidator);
    }

    @InitBinder("teacherForm")
    public void initTeacherBinder(WebDataBinder binder) {
        binder.addValidators(userTeacherDTOValidator);
    }

    @GetMapping("/login")
    public String getLoginPage(@RequestParam(required = false) String error, final HttpServletRequest request,
                               final HttpSession session) {
        LOGGER.debug("Getting login page, error={}", error);

        String userIp = getUserIp(request);

        if(loginAttemptService.isCaptchaNeeded(userIp) && error == null){
            session.setAttribute("SPRING_SECURITY_LAST_EXCEPTION", "captchaNeeded");
            return "redirect:/login?error=true";
        }
        return "auth/login";
    }

    @PostMapping("/old/registration-student")
    public String regsterStudent(@Valid @ModelAttribute("studentForm") UserStudentDTO userStudentDTO,
                             BindingResult bindingResult, final HttpServletRequest request, Model model) {

        LOGGER.debug("Processing user registration userForm={}, bindingResult={}", userStudentDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            LOGGER.debug("Validation error.");
            model.addAttribute("message", getMessageFromBindingResult(bindingResult));
            return "auth/badUser";
        }

        User registered;
        try{
            registered = userService.registerStudent(userStudentDTO);
        } catch(DataIntegrityViolationException e) {
            LOGGER.warn("Exception occurred when trying to save the user, assuming duplicate email", e);
            model.addAttribute("message", "Exception occurred when trying to save the user, assuming duplicate email");
            return "auth/badUser";
        }

        try {
            final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), appUrl));
            model.addAttribute("emailMessage", registered.getEmail());
        } catch (final Exception ex) {
            LOGGER.warn("Unable to register user", ex);
            model.addAttribute("message", "Unable to send verification email");
            return "auth/badUser"; // TODO
        }

        return "/auth/verifyYourAccount";
    }

    @PostMapping("/old/registration-teacher")
    public String registerTeacher(@Valid @ModelAttribute("teacherForm") UserTeacherDTO userTeacherDTO,
                             BindingResult bindingResult, final HttpServletRequest request, Model model) {

        LOGGER.debug("Processing user registration userForm={}, bindingResult={}", userTeacherDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            LOGGER.debug("Validation error.");
            model.addAttribute("message", getMessageFromBindingResult(bindingResult));
            return "auth/badUser";
        }

        User registered;
        try{
            registered = userService.registerTeacher(userTeacherDTO);
        } catch(DataIntegrityViolationException e) {
            LOGGER.warn("Exception occurred when trying to save the user, assuming duplicate email", e);
            model.addAttribute("message", "Exception occurred when trying to save the user, assuming duplicate email");
            return "auth/badUser";
        }

        try {
            final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), appUrl));
            model.addAttribute("emailMessage", registered.getEmail());
        } catch (final Exception ex) {
            LOGGER.warn("Unable to send verification email", ex);
            model.addAttribute("message", "Unable to send verification email");
            return "auth/badUser"; // TODO
        }

        return "/auth/verifyYourAccount";
    }

//    NON API

    private String getUserIp(final HttpServletRequest request) {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

    private String getMessageFromBindingResult(BindingResult bindingResult) {
        String message = "Validation error";
        for (Object object : bindingResult.getAllErrors()) {
            if(object instanceof FieldError) {
                FieldError fieldError = (FieldError) object;
                message = fieldError.getDefaultMessage();
            }

            if(object instanceof ObjectError) {
                ObjectError objectError = (ObjectError) object;
                message = objectError.getDefaultMessage();
            }
        }
        return message;
    }
}
