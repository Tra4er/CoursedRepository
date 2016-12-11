package com.coursed.controller.mvc;

import com.coursed.dto.StudentRegistrationForm;
import com.coursed.dto.TeacherRegistrationForm;
import com.coursed.dto.UserRegistrationForm;
import com.coursed.model.Student;
import com.coursed.model.auth.User;
import com.coursed.model.auth.VerificationToken;
import com.coursed.registration.OnRegistrationCompleteEvent;
import com.coursed.security.SecurityService;
import com.coursed.service.UserService;
import com.coursed.validator.UserRegistrationFormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;

/**
 * Created by Hexray on 13.11.2016.
 */
@Controller
public class AuthController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @GetMapping("/registration")
    public ModelAndView registration() {
        LOGGER.debug("Sending userForm to client");
        return new ModelAndView("auth/registration", "userForm", new UserRegistrationForm());
    }

    @GetMapping("/login")
    public String getLoginPage(Model model, @RequestParam(required = false) String error) {
//      TODO  Send dto: studentDTO or teacherDTO
        LOGGER.debug("Getting login page, error={}", error);
        model.addAttribute("error", error);
        return "auth/login";
    }
}
