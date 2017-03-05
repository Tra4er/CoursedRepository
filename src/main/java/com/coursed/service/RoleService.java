package com.coursed.service;

import com.coursed.dto.RoleDTO;
import com.coursed.model.auth.Role;

import java.util.List;

/**
 * Created by Hexray on 26.11.2016.
 */
public interface RoleService {
    List<RoleDTO> getAll();
}
