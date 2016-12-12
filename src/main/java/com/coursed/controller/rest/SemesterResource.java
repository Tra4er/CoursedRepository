package com.coursed.controller.rest;

import com.coursed.model.Semester;
import com.coursed.service.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Created by Trach on 12/12/2016.
 */
@RestController
@RequestMapping("/api/semesters")
public class SemesterResource {

    @Autowired
    private SemesterService semesterService;

    @GetMapping("/getAll")
    public Collection<Semester> getAll() {

        System.out.println(semesterService.findAll());
        return semesterService.findAll();
    }
}
