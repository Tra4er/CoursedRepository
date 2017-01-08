package com.coursed.security;

import com.coursed.captcha.CaptchaService;
import com.coursed.model.auth.Role;
import com.coursed.model.auth.User;
import com.coursed.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Trach on 11/6/2016.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private HttpServletRequest request;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        LOGGER.debug("Authenticating user with email={}", email.replaceFirst("@.*", "@***"));

        String userIp = getUserIp();

        if(loginAttemptService.isCaptchaNeeded(userIp)){
            if(request.getParameter("g-recaptcha-response") != null) {
                try {
                    captchaService.processResponse(request.getParameter("g-recaptcha-response"));
                } catch (Exception e) {
                    throw new RuntimeException("captchaError");
                }

            } else {
                throw new RuntimeException("captchaNeeded");
            }
        }

        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        User user = userRepository.findOneByEmail(email);
        if(user == null) {
            throw new UsernameNotFoundException(String.format("User with email=%s was not found", email));
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), user.isEnabled(), accountNonExpired, credentialsNonExpired,
                accountNonLocked, grantedAuthorities);
    }

//    NON API

    private String getUserIp() {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

}
