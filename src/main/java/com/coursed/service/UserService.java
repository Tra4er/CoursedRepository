package com.coursed.service;

import com.coursed.entities.User;

/**
 * Created by Hexray on 06.11.2016.
 */
public interface UserService {
    void createUser(User user);
    User findUserById(Long id);
    Iterable<User> findAll();
}
