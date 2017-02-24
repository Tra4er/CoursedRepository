package com.coursed.controller.rest;

import com.coursed.dto.YearDTO;
import com.coursed.model.Year;
import com.coursed.service.SemesterService;
import com.coursed.service.YearService;
import com.coursed.util.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Hexray on 16.11.2016.
 */
@RestController
@RequestMapping("/api/years")
public class YearResource {

    @Autowired
    private YearService yearService;

    @Autowired
    private SemesterService semesterService;

    @GetMapping
    public ResponseEntity<GenericResponse> get(@RequestParam(value = "page") Integer page,
                                               @RequestParam(value = "size") Integer size) {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                yearService.getAll(page, size)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GenericResponse> post(@RequestBody YearDTO yearDTO) { // TODO @Valid
        Year year = yearService.create(yearDTO);
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success", year.getId()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse> getById(@PathVariable(value="id") Long yearId) {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                yearService.getById(yearId)), HttpStatus.OK);
    }

    @GetMapping("/{id}/semesters")
    public ResponseEntity<GenericResponse> getSemesters(@PathVariable(value="id") Long yearId,
                                                        @RequestParam(value = "page") Integer page,
                                                        @RequestParam(value = "size") Integer size) {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                semesterService.getAllByYear(yearId, page, size)), HttpStatus.OK);
    }

    @GetMapping("/current")
    private ResponseEntity<GenericResponse> getCurrentYear() {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success", yearService.getCurrent()),
                HttpStatus.OK);
    }
}