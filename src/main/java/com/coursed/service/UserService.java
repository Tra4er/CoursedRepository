package com.coursed.service;

import com.coursed.dto.UserRegistrationForm;
import com.coursed.model.auth.Role;
import com.coursed.model.auth.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Hexray on 06.11.2016.
 * Changed by Trach on 07.11.2016
 */
public interface UserService {
    void register(User user);
    User getUserById(Long id);
    Optional<User> getUserByEmail(String email);
    List<User> findAll();
    void addRole(Long userId, Long roleId);
}
