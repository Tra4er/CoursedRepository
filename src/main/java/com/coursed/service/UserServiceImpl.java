package com.coursed.service;

import com.coursed.dto.UserRegistrationForm;
import com.coursed.model.Role;
import com.coursed.model.User;
import com.coursed.repository.RoleRepository;
import com.coursed.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
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

    @Override
    public void save(UserRegistrationForm userForm) {

        User user = new User();
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());

        user.setStudent(false);
        user.setTeacher(false);
        user.setEmailConfirmed(false);
        user.setRoleConfirmed(false);
        user.setRegistrationDate(new Date());
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        Set<Role> roles = new HashSet<Role>();
        roles.add(roleRepository.findOne(1L));
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public void tempSave(User user) {
        user.setStudent(false);
        user.setTeacher(false);
        user.setEmailConfirmed(false);
        user.setRoleConfirmed(false);
        user.setRegistrationDate(new Date());
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        Set<Role> roles = new HashSet<Role>();
        roles.add(roleRepository.findOne(1L));
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

}
