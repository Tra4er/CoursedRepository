package com.coursed.service;

import com.coursed.model.auth.Role;

import java.util.List;

/**
 * Created by Hexray on 26.11.2016.
 */
public interface RoleService {
    void create(Role role);
    List<Role> findAll();
}
