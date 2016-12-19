package com.coursed.controller.rest;

import com.coursed.model.Teacher;
import com.coursed.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Created by Hexray on 17.12.2016.
 */
@RestController
@RequestMapping("/api/teachers")
public class TeacherResource {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/getAllWithDiscipline")
    Collection<Teacher> getAllTeachersWithDiscipline(@RequestParam(name = "disciplineId") Long disciplineId){
        return teacherService.findAllTeachersWithDiscipline(disciplineId);
    }

    @GetMapping("/getAllWithoutDiscipline")
    Collection<Teacher> getAllTeachersWithoutDiscipline(@RequestParam(name = "disciplineId") Long disciplineId){
        return teacherService.findAllTeachersWithoutDiscipline(disciplineId);
    }
}
