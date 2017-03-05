package com.coursed.registration.listener;

import com.coursed.controller.mvc.AuthController;
import com.coursed.dto.UserDTO;
import com.coursed.model.auth.User;
import com.coursed.registration.OnRegistrationCompleteEvent;
import com.coursed.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by Trach on 12/3/2016.
 */
@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        UserDTO userDTO = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.createVerificationTokenForUser(userDTO, token);

        String recipientAddress = userDTO.getEmail();
        String subject = "Підтвердження реєстрації на CoursEd";
        String confirmationUrl
                = event.getAppUrl() + "/users/confirmRegistration?token=" + token;
        String message = "Щоб підтвердити свій E-Mail, перейдіть по цьому посиланню: ";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + confirmationUrl);
        LOGGER.debug("Sending verification email: {}", email);
        javaMailSender.send(email);
    }
}
