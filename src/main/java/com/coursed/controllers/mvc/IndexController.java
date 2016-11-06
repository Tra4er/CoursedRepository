package com.coursed.controllers.mvc;

import com.coursed.entities.Role;
import com.coursed.entities.User;
import com.coursed.repository.RoleRepository;
import com.coursed.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Hexray on 31.10.2016.
 */
@Controller
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping("/")
    public String index() {
//        System.out.println("==**===============Base configuration has been loaded");
//
//        Role studentRole = new Role();
//        studentRole.setName("ROLE_STUDENT");
//
//        Role adminRole = new Role();
//        adminRole.setName("ROLE_TEACHER");
//
//        roleRepository.save(studentRole);
//        roleRepository.save(adminRole);
//
//        Set<Role> studentRoles = new HashSet<>();
//        studentRoles.add(studentRole);
//
//        Set<Role> adminRoles = new HashSet<>();
//        adminRoles.add(adminRole);
//
//
//        User student = new User();
//        student.setEmail("student@mail.u");
//        student.setPassword("test");
//        student.setRoles(studentRoles);
//
//        User admin = new User();
//        admin.setEmail("admin@mail.u");
//        admin.setPassword("testadmin");
//        admin.setRoles(adminRoles);
//
//
//        userService.save(student);
//        userService.save(admin);

        return "index";
    }
}
