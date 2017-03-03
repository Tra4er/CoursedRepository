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
    private LoginAttemptService loginAttemptService;

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
            session.setAttribute("SPRING_SECURITY_LAST_EXCEPTION", "CaptchaNeeded");
            return "redirect:/login?error=true";
        }
        return "auth/login";
    }

//    NON API

    private String getUserIp(final HttpServletRequest request) {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

}
