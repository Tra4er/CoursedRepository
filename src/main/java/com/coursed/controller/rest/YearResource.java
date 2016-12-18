package com.coursed.controller.rest;

import com.coursed.dto.YearDTO;
import com.coursed.model.Semester;
import com.coursed.model.Year;
import com.coursed.service.YearService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Created by Hexray on 16.11.2016.
 */
@RestController
@RequestMapping("/api/years")
public class YearResource {
    @Autowired
    private YearService yearService;

    @GetMapping("/getAll")
    private Collection<Year> getYears() {
        return yearService.findAll();
    }

    //TODO: transfer into semesterResource, todo2: change to parameters
    @GetMapping("/getSemestersFromYear/{id}")
    private Collection<Semester> getSemesters(@PathVariable(value="id") Long yearId) {
        ObjectMapper mapper = new ObjectMapper();

        return yearService.findOne(yearId).getSemesters();
    }

    @PostMapping("/create")
    private Year createYear(@RequestBody YearDTO yearDTO) {
       return yearService.create(yearDTO);
    }

    @GetMapping("/getCurrent")
    private Year getCurrentYear() {
        return yearService.getCurrent();
    }
}