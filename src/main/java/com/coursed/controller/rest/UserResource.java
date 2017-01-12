package com.coursed.controller.rest;

import com.coursed.captcha.CaptchaService;
import com.coursed.dto.*;
import com.coursed.error.exception.*;
import com.coursed.model.auth.PasswordResetToken;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

/**
 * Created by Trach on 11/24/2016.
 */
@RestController
@RequestMapping("/api/users")
public class UserResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

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

    @GetMapping("/sendNewRegistrationToken")
    @ResponseBody
    public ResponseEntity<OldGenericResponse> sendNewRegistrationToken(@RequestParam String existingToken,
                                                                       final HttpServletRequest request) {
        VerificationToken newToken = userService.generateNewVerificationToken(existingToken);
        User user = userService.getUserByVerificationToken(newToken.getToken());
        mailSender.send(constructResendVerificationTokenEmail(getAppUrl(request), newToken, user));
        return new ResponseEntity<>(new OldGenericResponse("success"), HttpStatus.OK);
    }

    @GetMapping("/resendRegistrationToken")
    @ResponseBody
    public ResponseEntity<OldGenericResponse> resendRegistrationToken(@RequestParam String email,
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

    @GetMapping("/sendResetPasswordToken")
    @ResponseBody
    public ResponseEntity<OldGenericResponse> sendResetPasswordToken(@RequestParam String email, final HttpServletRequest request) {

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

//  In case user forgot his password and resets it by sending resetToken to email.
    @PostMapping("/savePassword")
    @ResponseBody
    public ResponseEntity<OldGenericResponse> savePassword(@Valid @RequestBody PasswordDTO passwordDTO) {
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
        return new ResponseEntity<>(new OldGenericResponse("success"), HttpStatus.OK);
    }

    //  In case user remembers his password and wont to update it.
    @PostMapping("/updatePassword")
    @ResponseBody
    public ResponseEntity<OldGenericResponse> changeUserPassword(@Valid @RequestBody PasswordDTO passwordDTO) {
        final User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!userService.checkIfValidOldPassword(user, passwordDTO.getOldPassword())) {
            throw new InvalidOldPasswordException();
        }
        userService.changeUserPassword(user, passwordDTO.getNewPassword());
        return new ResponseEntity<>(new OldGenericResponse("success"), HttpStatus.OK);
    }

    @GetMapping("/checkEmail")
    private boolean checkEmail(@RequestParam("email") String email) {
        return userService.getUserByEmail(email) != null;
    }

    @GetMapping("/getUser")
    private User getUser(@RequestParam("email") String email) {
        return userService.getUserByEmail(email);
    }

    @PostMapping("/confirm-teacher")
    public void confirmTeacher(@RequestParam(name = "userId") Long userId) {
        userService.makeATeacher(userId);
    }

    //TODO: solve n+1 JPA problem via avoiding traversal of unfetched entities when JSON is creating
//    @GetMapping("/getAllUnconfirmedTeachers")
//    private Set<Object> getAllUnconfirmedTeachers() {
//
//        Set<Object> json = new HashSet<>();
//
//        List<User> UnconfirmedTeachers = userService.findAllUnconfirmedTeachers();
//
//        for (User user : UnconfirmedTeachers) {
//            Map<String, Object> value = new HashMap<>();
//
//            value.put("id", user.getId());
//            value.put("email", user.getEmail());
//
//            json.add(value);
//        }
//
//        return json;
//    }

    @GetMapping("/getAllUnconfirmedTeachers")
    private Collection<User> getAllUnconfirmedTeachers() {
        return userService.findAllUnconfirmedTeachers();
    }

    @GetMapping("/getAllTeachers") // TODO create separate controller
    private Collection<User> getAllTeachers(@RequestParam(name = "groupId", required = false) Long groupId) {
        return userService.findAllTeachers(groupId);
    }

    @GetMapping("/getAllGroupCurators")
    private Collection<User> getAllGroupCurators(@RequestParam(name = "groupId") Long groupId) {
        return userService.findAllGroupCurators(groupId);
    }

    @GetMapping("/deleteUser") // TODO delete method
    private void deleteUser(@RequestParam(name = "userId") Long userId) {
        userService.deleteUser(userId);
    }

    //    NON API

    private SimpleMailMessage constructResendVerificationTokenEmail(String contextPath, VerificationToken token,
                                                                    User user) {
        String confirmationUrl = contextPath + "/users/confirmRegistration?token=" + token.getToken();
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
