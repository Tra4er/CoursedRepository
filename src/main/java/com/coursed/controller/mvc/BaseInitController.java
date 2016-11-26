package com.coursed.controller.mvc;

import com.coursed.model.Semester;
import com.coursed.model.Year;
import com.coursed.model.auth.Role;
import com.coursed.model.auth.User;
import com.coursed.model.enums.SemesterNumber;
import com.coursed.repository.SemesterRepository;
import com.coursed.repository.YearRepository;
import com.coursed.service.RoleService;
import com.coursed.service.UserService;
import com.coursed.service.YearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Hexray on 31.10.2016.
 */
@Controller
public class BaseInitController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private YearService yearService;

    @Transactional
    @RequestMapping("/valve")
    public String index() {

        Role registeredRole = new Role("ROLE_REGISTERED", null);
        Role studentRole = new Role("ROLE_STUDENT", null);
        Role teacherRole = new Role("ROLE_TEACHER", null);
        roleService.create(registeredRole);
        roleService.create(studentRole);
        roleService.create(teacherRole);

        User student = new User();
        student.setEmail("student@s.s");
        student.setPassword("123");

        User teacher = new User();
        teacher.setEmail("teacher@t.t");
        teacher.setPassword("123");

        userService.register(student);
        userService.register(teacher);

        userService.connectUserWithRole(student, studentRole);
        userService.connectUserWithRole(teacher, teacherRole);

        Year year = new Year(2015);
        yearService.create(year);

        System.out.println("==>Base configuration has been loaded");
        return "redirect:/login";
    }
}
