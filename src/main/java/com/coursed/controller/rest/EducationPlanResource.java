package com.coursed.controller.rest;

import com.coursed.dto.EducationPlanDTO;
import com.coursed.service.EducationPlanService;
import com.coursed.util.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Hexray on 11.12.2016.
 */
@RestController
@RequestMapping("/api/educationPlans")
public class EducationPlanResource {

    @Autowired
    private EducationPlanService educationPlanService;

    @GetMapping
    public ResponseEntity<GenericResponse> get(@RequestParam(value = "page") Integer page,
                                               @RequestParam(value = "size") Integer size) {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                educationPlanService.getAll(page, size)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GenericResponse> post(@RequestBody EducationPlanDTO planForm) {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                educationPlanService.create(planForm)), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<GenericResponse> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                educationPlanService.getById(id)), HttpStatus.OK);
    }

}
