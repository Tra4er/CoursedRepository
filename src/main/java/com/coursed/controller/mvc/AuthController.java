package com.coursed.controller.mvc;

import com.coursed.dto.UserRegistrationForm;
import com.coursed.model.auth.User;
import com.coursed.model.auth.VerificationToken;
import com.coursed.registration.OnRegistrationCompleteEvent;
import com.coursed.service.SecurityService;
import com.coursed.service.UserService;
import com.coursed.validator.UserRegistrationFormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.Locale;
import java.util.Optional;

/**
 * Created by Hexray on 13.11.2016.
 */
@Controller
//@RequestMapping("/auth") TODO
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserRegistrationFormValidator userRegistrationFormValidator;

    @Autowired
    private SecurityService securityService;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @InitBinder("userForm")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userRegistrationFormValidator);
    }

    //    @GetMapping("/registration") TODO
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        LOGGER.debug("Sending userForm to client");
        return new ModelAndView("auth/registration", "userForm", new UserRegistrationForm());
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@Valid @ModelAttribute("userForm") UserRegistrationForm userForm,
                               BindingResult bindingResult, final HttpServletRequest request) {

        LOGGER.debug("Processing user registration userForm={}, bindingResult={}", userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "auth/registration";
        }

        User user = new User();
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());

        User registered;

        try{
            registered = userService.register(user);
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

//        securityService.autoLogin(userForm.getEmail(), userForm.getPassword()); // TODO read about

        return "/auth/verifyYourAccount";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String confirmRegistration(Model model) {
        System.out.println("TEST");
        String message = "Hello";
        return "redirect:/login?message=" + message;
    }

    @RequestMapping(value = "/registrationConfirm", method = RequestMethod.GET)
    public String confirmRegistration(Model model, @RequestParam("token") String token) {
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
        String message = "Ваш акаунт був активований. Будь ласка, увійдіть.";
        return "redirect:/login?message=" + message;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage(Model model, @RequestParam(required = false, name = "message") String message,
                               @RequestParam(required = false, name="error") String error) {
        LOGGER.debug("Getting login page, error={}", error);
        model.addAttribute("error", error);
        model.addAttribute("message", message);
        return "auth/login";
    }


}
