package com.coursed.error.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Trach on 12/20/2016.
 */
@Component("authenticationFailureHandler")
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    public CustomAuthenticationFailureHandler() {
    }

    @Override
    public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response,
                                        final AuthenticationException exception) throws IOException, ServletException {
        setDefaultFailureUrl("/login?error=true");

        super.onAuthenticationFailure(request, response, exception);


        String errorMessage = "Невірний емейл або пароль.";

        if (exception.getMessage().equalsIgnoreCase("User is disabled")) {
            errorMessage = "Профіль не активований.";
        } else if (exception.getMessage().equalsIgnoreCase("User account has expired")) {
            errorMessage = "Профіль не активований. Час на активацію вийшов.";
        } else if (exception.getMessage().equalsIgnoreCase("blocked")) {
            errorMessage = "Профіль заблокований.";
        } else if (exception.getMessage().equalsIgnoreCase("CaptchaNeeded")) {
            errorMessage = "CaptchaNeeded";
        } else if (exception.getMessage().equalsIgnoreCase("CaptchaError")) {
            errorMessage = "CaptchaError";
        }

        request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
    }
}
