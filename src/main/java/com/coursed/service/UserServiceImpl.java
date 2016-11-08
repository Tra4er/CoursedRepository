package com.coursed.service;

import com.coursed.model.Role;
import com.coursed.model.User;
import com.coursed.repository.RoleRepository;
import com.coursed.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Hexray on 06.11.2016.
 * Edited by Trach on 07.11.2016
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    //    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setStudent(false);
        user.setTeacher(false);
        user.setEmailConfirmed(false);
        user.setRoleConfirmed(false);
        user.setRegistrationDate(new Date());
        user.setPassword(user.getPassword());
        Set<Role> roles = new HashSet<Role>();
        roles.add(roleRepository.findOne(1L));
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

}
