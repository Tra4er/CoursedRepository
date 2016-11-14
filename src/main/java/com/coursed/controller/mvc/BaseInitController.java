package com.coursed.controller.mvc;

import com.coursed.model.Year;
import com.coursed.repository.RoleRepository;
import com.coursed.repository.SemesterRepository;
import com.coursed.repository.YearRepository;
import com.coursed.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Hexray on 31.10.2016.
 */
@Controller
public class BaseInitController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private YearRepository yearRepository;

    @RequestMapping("/valve")
    public String index() {
//
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
//        userService.tempSave(student);
//        userService.tempSave(admin);
//        System.out.println("==**===============Base configuration has been loaded");
//
//
//        Year year = new Year();
//        year.setBeginYear(2015);
//
//        Semester sem1 = new Semester();
//        sem1.setSemesterNumber("first");
//
//        Semester sem2 = new Semester();
//        sem2.setSemesterNumber("second");
//
//        yearRepository.save(year);
//
//        sem1.setYear(year);
//
//        sem2.setYear(year);
//
//        semesterRepository.save(sem1);
//        semesterRepository.save(sem2);

        Year year3 = yearRepository.findOne(1L);

        return "redirect:/login";
    }
}
