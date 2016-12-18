package com.coursed.controller.mvc;

import com.coursed.model.auth.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

/**
 * Created by Trach on 12/10/2016.
 */
@Controller
public class EventsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventsController.class);

    @GetMapping("/events") // TODO config access to rest
    public String events(RedirectAttributes redAtt, Authentication authentication) {
        if(hasRole("ROLE_HEAD") || hasRole("ROLE_SECRETARY")) { // TODO add more roles if needed
            return "/events/events-head";
        }
        if(hasRole("ROLE_TEACHER")) {
            return "/events/events-teacher";
        }
        if(hasRole("ROLE_STUDENT")) {
            return "/events/events-student";
        }
        redAtt.addFlashAttribute("message", "You do not have access to this page.");
        return "/auth/badUser"; // TODO redirect:error page
    }

    @GetMapping("/events/{eventId}")
    public String getEvent(@PathVariable String eventId, Model model) {
        model.addAttribute("eventId", eventId);
        return "/events/event-public";
    }


    // ============== NON-API ============
    private boolean hasRole(String role) {
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        boolean hasRole = false;
        for (GrantedAuthority authority : authorities) {
            hasRole = authority.getAuthority().equals(role);
            if (hasRole) {
                break;
            }
        }
        return hasRole;
    }

}
