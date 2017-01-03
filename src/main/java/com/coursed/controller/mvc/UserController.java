package com.coursed.controller.mvc;

import com.coursed.model.auth.PasswordResetToken;
import com.coursed.model.auth.User;
import com.coursed.model.auth.VerificationToken;
import com.coursed.security.SecurityService;
import com.coursed.service.PasswordResetTokenService;
import com.coursed.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Trach on 12/24/2016.
 */
@Controller
@RequestMapping("/users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @GetMapping("/confirmRegistration")
    public String confirmRegistration(@RequestParam("token") String token, RedirectAttributes redAtt) {
        LOGGER.debug("Receiving confirmation token: {}", token);

        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) { // TODO
            LOGGER.debug("Invalid token received: {}", token);
            String message = "InvalidTokenReceived: " + token;
            redAtt.addFlashAttribute("message", message);
            return "redirect:/users/badUser";
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            LOGGER.debug("Verification token expired for user: {}", user);
            String messageValue = "VerificationTokenExpired";
            redAtt.addFlashAttribute("token", token);
            redAtt.addFlashAttribute("message", messageValue);
            return "redirect:/users/badUser";
        }

        user.setEnabled(true);
        userService.saveRegisteredUser(user);
        LOGGER.debug("Received verification from user: {}", user);
        redAtt.addFlashAttribute("message", "Ви активували свій акаунт. Увійдіть.");
        return "redirect:/login";
    }

    @GetMapping("/forgotPassword")
    public String forgotPassword(Model model) {
        model.addAttribute("userProblem", "ForgotPassword");
        return "/auth/sendEmail";
    }

    @GetMapping("/resendRegistrationToken")
    public String resendRegistrationToken(Model model) {
        model.addAttribute("userProblem", "ResendRegistrationToken");
        return "/auth/sendEmail";
    }

    @GetMapping("/changePassword")
    public String verifyTokenForChangePass(@RequestParam("id") Long id, @RequestParam("token") String token,
                                           RedirectAttributes redAtt) {
        LOGGER.debug("Validating password reset token: " + token + " and id: " + id);
        PasswordResetToken passToken = passwordResetTokenService.getByToken(token);
        if ((passToken == null) || (passToken.getUser().getId() != id)) {
            redAtt.addFlashAttribute("message", "InvalidToken");
            return "redirect:/users/badUser";
        }

        Calendar cal = Calendar.getInstance();
        if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            redAtt.addFlashAttribute("message", "Expired");
            return "redirect:/users/badUser";
        }

        redAtt.addFlashAttribute("token", token);
        redAtt.addAttribute("by", "email");
        return "redirect:/users/updatePassword";
    }

    @GetMapping("/updatePassword")
    public String updatePassword(@RequestParam(value = "by", required = false) String message) {
        if (message != null) {
            if (message.equals("email")) {
                return "/auth/savePassword";
            } else if (message.equals("oldPassword")) {
                return "/auth/updatePassword";
            }
        }
        return "redirect:/users/badUser";
    }

    @GetMapping("/badUser")
    public String getBadUser() {
        return "auth/badUser";
    }

    // ============== NON-API ============
//    private boolean hasRole(String role) {
//        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)
//                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
//        boolean hasRole = false;
//        for (GrantedAuthority authority : authorities) {
//            hasRole = authority.getAuthority().equals(role);
//            if (hasRole) {
//                break;
//            }
//        }
//        return hasRole;
//    }
}
