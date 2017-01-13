package com.coursed.controller.rest;

import com.coursed.dto.YearDTO;
import com.coursed.model.Semester;
import com.coursed.model.Year;
import com.coursed.service.YearService;
import com.coursed.util.GenericResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.net.www.content.text.Generic;

import java.util.Collection;

/**
 * Created by Hexray on 16.11.2016.
 */
@RestController
@RequestMapping("/api/years")
public class YearResource {
    @Autowired
    private YearService yearService;

    @GetMapping
    public ResponseEntity<GenericResponse> get() {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                yearService.findAll()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GenericResponse> post(@RequestBody YearDTO yearDTO) { // TODO @Valid
        Year year = yearService.create(yearDTO);
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success", year.getId()), HttpStatus.OK);
    }

    @GetMapping("/{id}/semesters")
    public ResponseEntity<GenericResponse> getSemesters(@PathVariable(value="id") Long yearId) {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                yearService.findOne(yearId).getSemesters()), HttpStatus.OK);
    }

    @GetMapping("/current")
    private ResponseEntity<GenericResponse> getCurrentYear() {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success", yearService.getCurrent()),
                HttpStatus.OK);
    }
}