package com.coursed.registration;

import com.coursed.dto.UserDTO;
import com.coursed.model.auth.User;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

/**
 * Created by Trach on 12/3/2016.
 */
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private final String appUrl;
    private final Locale locale;
    private final UserDTO userDTO;

    public OnRegistrationCompleteEvent(final UserDTO userDTO, final Locale locale, final String appUrl) {
        super(userDTO);
        this.userDTO = userDTO;
        this.locale = locale;
        this.appUrl = appUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public UserDTO getUser() {
        return userDTO;
    }
}
