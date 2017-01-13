package com.coursed.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login*", "/users/confirmRegistration", "/users/forgotPassword", "/users/changePassword",
                        "/users/updatePassword", "/users/resendRegistrationToken", "/test", "/valve", "/users/badUser"
                        ).permitAll()
                // REST
                .antMatchers(HttpMethod.POST, "/api/students", "/api/teachers").permitAll()
//                .antMatchers(HttpMethod.GET, "/api/years", "/api/specialities", "/api/groups").permitAll()
                .antMatchers("/api/account/*", "/api/years/getAll", "/api/specialities/getAll", "/api/groups/getAll",
                        "/api/years/getSemestersFromYear/*"
                ).permitAll() // TODO config resources for anonymous using @PreAuthorize("hasRole('SOME_ROLE')")
                .antMatchers("/", "/**").hasAnyRole("REGISTERED", "STUDENT", "TEACHER", "ADMIN")
                .antMatchers("/css/**", "/js/**", "/fonts/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .failureHandler(authenticationFailureHandler)
                .usernameParameter("email")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .deleteCookies("remember-me")
                .logoutSuccessUrl("/login")
                .permitAll()
                .and()
                .rememberMe();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

}