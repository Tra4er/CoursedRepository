package com.coursed.service.implementation;

import com.coursed.dto.RoleDTO;
import com.coursed.model.auth.Role;
import com.coursed.repository.RoleRepository;
import com.coursed.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Hexray on 26.11.2016.
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<RoleDTO> getAll() {
        return roleRepository.findAllInDTO();
    }
}
