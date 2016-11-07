package com.coursed.service;

import com.coursed.model.User;

/**
 * Created by Hexray on 06.11.2016.
 * Changed by Trach on 07.11.2016
 */
public interface UserService {
    void save(User user);
    User findUserById(Long id);
    Iterable<User> findAll();
}
