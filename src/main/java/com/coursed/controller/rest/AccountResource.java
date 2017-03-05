package com.coursed.controller.rest;

import com.coursed.dto.PasswordDTO;
import com.coursed.error.exception.InvalidOldPasswordException;
import com.coursed.error.exception.InvalidPasswordResetTokenException;
import com.coursed.error.exception.TokenNotFoundException;
import com.coursed.error.exception.UserNotFoundException;
import com.coursed.model.auth.PasswordResetToken;
import com.coursed.model.auth.User;
import com.coursed.model.auth.VerificationToken;
import com.coursed.service.PasswordResetTokenService;
import com.coursed.service.UserService;
import com.coursed.service.VerificationTokenService;
import com.coursed.util.GenericResponse;
import com.coursed.validator.PasswordDTOValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;
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
    private VerificationTokenService verificationTokenService;

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    @Autowired
    private PasswordDTOValidator passwordDTOValidator;

    @InitBinder("passwordDTO")
    public void initPasswordBinder(WebDataBinder binder) {
        binder.addValidators(passwordDTOValidator);
    }

    @PostMapping("/sendNewRegistrationToken")
    public ResponseEntity sendNewRegistrationToken(@RequestParam String existingToken,
                                                                       final HttpServletRequest request) {
        VerificationToken newToken = userService.generateNewVerificationToken(existingToken);
        User user = userService.getUserByVerificationToken(newToken.getToken());
        mailSender.send(constructResendVerificationTokenEmail(getAppUrl(request), newToken, user));
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/resendRegistrationToken")
    public ResponseEntity resendRegistrationToken(@RequestParam("email") String email,
                                                                      final HttpServletRequest request) {
        User user = userService.getByEmail(email);
        if(user == null) {
            throw new UserNotFoundException();
        }
        VerificationToken token = verificationTokenService.getByUser(user);
        if(token == null) {
            throw new TokenNotFoundException();
        }
        mailSender.send(constructResendVerificationTokenEmail(getAppUrl(request), token, user));
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/sendResetPasswordToken")
    public ResponseEntity sendResetPasswordToken(@RequestParam("email") String email,
                                                                     final HttpServletRequest request) {

        User user = userService.getByEmail(email);
        if (user == null) {
            throw new UserNotFoundException();
        }

        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);
        String appUrl = " http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        SimpleMailMessage simpleMailMessage = constructResetTokenEmail(appUrl, token, user);
        mailSender.send(simpleMailMessage);
        return new ResponseEntity(new GenericResponse("sent"), HttpStatus.OK);
    }

    //  In case user forgot his password and resets it by sending resetToken to email.
    @PostMapping("/savePassword")
    public ResponseEntity savePassword(@Valid @RequestBody PasswordDTO passwordDTO) {
        String token = passwordDTO.getToken();
        LOGGER.debug("Validating password reset token: " + token);
        PasswordResetToken passToken = passwordResetTokenService.getByToken(token);
        if ((passToken == null)) {
            throw new InvalidPasswordResetTokenException("InvalidToken");
        }

        Calendar cal = Calendar.getInstance();
        if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            throw new InvalidPasswordResetTokenException("Expired");
        }

        User user = passwordResetTokenService.getUserByToken(token);

        userService.changeUserPassword(user, passwordDTO.getNewPassword());
        return new ResponseEntity(HttpStatus.OK);
    }

    //  In case user remembers his password and wont to update it.
    @PostMapping("/updatePassword")
    @PreAuthorize("hasRole('REGISTERED')")
    public ResponseEntity changeUserPassword(@Valid @RequestBody PasswordDTO passwordDTO) {
        final User user = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!userService.checkIfValidOldPassword(user, passwordDTO.getOldPassword())) {
            throw new InvalidOldPasswordException();
        }
        userService.changeUserPassword(user, passwordDTO.getNewPassword());
        return new ResponseEntity(HttpStatus.OK);
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
