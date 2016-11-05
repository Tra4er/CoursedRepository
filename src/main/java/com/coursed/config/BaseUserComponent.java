package com.coursed.config;

import com.coursed.entities.Account;
import com.coursed.entities.Role;
import com.coursed.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Hexray on 05.11.2016.
 */

public class BaseUserComponent {

    private AccountRepository accountRepository;


    private void Main()
    {
        System.out.println("==**===============Base configuration have been loaded");

        Role studentRole = new Role();
        studentRole.setName("ROLE_STUDENT");

        Role adminRole = new Role();
        studentRole.setName("ROLE_ADMIN");

        Set<Role> studentRoles = new HashSet<>();
        studentRoles.add(studentRole);

        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);


        Account student = new Account();
        student.setEmail("student@mail.u");
        student.setPassword("test");
        student.setRoles(studentRoles);

        Account admin = new Account();
        admin.setEmail("admin@mail.u");
        admin.setPassword("testadmin");
        admin.setRoles(adminRoles);


        accountRepository.save(student);
        accountRepository.save(admin);




    }
}
