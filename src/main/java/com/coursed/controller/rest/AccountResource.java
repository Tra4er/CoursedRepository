package com.coursed.controller.rest;

import com.coursed.captcha.CaptchaService;
import com.coursed.error.exception.TokenNotFoundException;
import com.coursed.error.exception.UserNotFoundException;
import com.coursed.model.auth.User;
import com.coursed.model.auth.VerificationToken;
import com.coursed.security.SecurityService;
import com.coursed.service.PasswordResetTokenService;
import com.coursed.service.TeacherService;
import com.coursed.service.UserService;
import com.coursed.service.VerificationTokenService;
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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * Created by Trach on 1/13/2017.
 */
@RestController
@RequestMapping("/api/account")
public class AccountResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountResource.class);

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

    @InitBinder("passwordDTO")
    public void initPasswordBinder(WebDataBinder binder) {
        binder.addValidators(passwordDTOValidator);
    }

    @PostMapping("/sendNewRegistrationToken")
    @ResponseBody
    public ResponseEntity<OldGenericResponse> sendNewRegistrationToken(@RequestParam String existingToken,
                                                                       final HttpServletRequest request) {
        VerificationToken newToken = userService.generateNewVerificationToken(existingToken);
        User user = userService.getUserByVerificationToken(newToken.getToken());
        mailSender.send(constructResendVerificationTokenEmail(getAppUrl(request), newToken, user));
        return new ResponseEntity<>(new OldGenericResponse("success"), HttpStatus.OK);
    }

    @PostMapping("/resendRegistrationToken")
    @ResponseBody
    public ResponseEntity<OldGenericResponse> resendRegistrationToken(@RequestParam("email") String email,
                                                                      final HttpServletRequest request) {
        User user = userService.getUserByEmail(email);
        if(user == null) {
            throw new UserNotFoundException();
        }
        VerificationToken token = verificationTokenService.getByUser(user);
        if(token == null) {
            throw new TokenNotFoundException();
        }
        mailSender.send(constructResendVerificationTokenEmail(getAppUrl(request), token, user));
        return new ResponseEntity<>(new OldGenericResponse("success"), HttpStatus.OK);
    }

    @PostMapping("/sendResetPasswordToken")
    @ResponseBody
    public ResponseEntity<OldGenericResponse> sendResetPasswordToken(@RequestParam("email") String email,
                                                                     final HttpServletRequest request) {

        User user = userService.getUserByEmail(email);
        if (user == null) {
            throw new UserNotFoundException();
        }

        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);
        String appUrl = " http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        SimpleMailMessage simpleMailMessage = constructResetTokenEmail(appUrl, token, user);
        mailSender.send(simpleMailMessage);
        return new ResponseEntity<>(new OldGenericResponse("success"), HttpStatus.OK);
    }

    //    NON API

    private SimpleMailMessage constructResendVerificationTokenEmail(String contextPath, VerificationToken token,
                                                                    User user) {
        String confirmationUrl = contextPath + "/users/confirmRegistration?email=" + user.getEmail() + "&token=" + token.getToken();
        String message = "Ми згенерували для вас новий ключ для підтвердження вашого E-Mail:";
        return constructEmail("Повторна відправка листа з підтвердженям", message + " \r\n" + confirmationUrl, user);
    }

    private SimpleMailMessage constructEmail(String subject, String body, User user) {
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        email.setFrom(env.getProperty("support.email"));
        return email;
    }

    private SimpleMailMessage constructResetTokenEmail(
            String contextPath, String token, User user) {
        String url = contextPath + "/users/changePassword?id=" + user.getId() + "&token=" + token;
        String message = "Відновлення паролю";
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject("Відновлення паролю");
        email.setText(message + url);
        email.setFrom(env.getProperty("support.email"));
        return email;
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
