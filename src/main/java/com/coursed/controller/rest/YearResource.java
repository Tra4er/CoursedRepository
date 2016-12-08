package com.coursed.controller.rest;

import com.coursed.model.Semester;
import com.coursed.model.Year;
import com.coursed.service.YearService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Created by Hexray on 16.11.2016.
 */
@RestController
public class YearResource {
    @Autowired
    private YearService yearService;

    @GetMapping("/api/years/getAll")
    private Collection<Year> getYears() {
        return yearService.findAll();
    }
    //TODO: transfer into semesterResource, todo2: change to parameters
    @GetMapping("/api/years/getSemestersFromYear/{id}")
    private Collection<Semester> getSemesters(@PathVariable(value="id") Long yearId) {
        return yearService.findOne(yearId).getSemesters();
    }

    @PostMapping("/api/years/create")
    private void createYear(@RequestBody Year year) {
        yearService.create(year);
    }


}