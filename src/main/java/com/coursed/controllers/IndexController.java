package com.coursed.controllers;

import com.coursed.entities.Account;
import com.coursed.entities.Role;
import com.coursed.repository.AccountRepository;
import com.coursed.repository.RoleRepository;
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

//    @Autowired
//    private AccountRepository accountRepository;
//
//    @Autowired
//    private RoleRepository roleRepository;

    @RequestMapping("/")
    public String index() {
//        System.out.println("==**===============Base configuration have been loaded");
//
//        Role studentRole = new Role();
//        studentRole.setName("ROLE_STUDENT");
//
//        Role adminRole = new Role();
//        adminRole.setName("ROLE_ADMIN");
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
//        Account student = new Account();
//        student.setEmail("student@mail.u");
//        student.setPassword("test");
//        student.setRoles(studentRoles);
//
//        Account admin = new Account();
//        admin.setEmail("admin@mail.u");
//        admin.setPassword("testadmin");
//        admin.setRoles(adminRoles);
//
//
//        accountRepository.save(student);
//        accountRepository.save(admin);

        return "index";
    }
}
