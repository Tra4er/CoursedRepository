package com.coursed.controller.rest;

import com.coursed.model.Student;
import com.coursed.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Trach on 12/13/2016.
 */
@RestController
@RequestMapping("/api/students")
public class StudentResource {

    @Autowired
    private StudentService studentService;

    @GetMapping("/getAll")
    public List<Student> getAll() {
        return studentService.findAll();
    }
}
