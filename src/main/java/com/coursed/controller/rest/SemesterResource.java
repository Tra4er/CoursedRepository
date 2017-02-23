package com.coursed.controller.rest;

import com.coursed.service.GroupService;
import com.coursed.service.SemesterService;
import com.coursed.util.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Trach on 2/23/2017.
 */
@RestController
@RequestMapping("/api/semesters")
public class SemesterResource {

    @Autowired
    private SemesterService semesterService;

    @Autowired
    private GroupService groupService;

    @GetMapping // TODO Do we need this?
    private ResponseEntity<GenericResponse> get(@RequestParam(value = "page") Integer page,
                                                @RequestParam(value = "size") Integer size) {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                semesterService.getAll(page, size)), HttpStatus.OK);
    }

    @GetMapping("{semesterId}")
    private ResponseEntity<GenericResponse> getById(@PathVariable("semesterId") Long semesterId) {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                semesterService.getById(semesterId)), HttpStatus.OK);
    }

    @GetMapping("{semesterId}/groups")
    private ResponseEntity<GenericResponse> getGroups(@PathVariable("semesterId") Long semesterId,
                                                      @RequestParam(value = "page") Integer page,
                                                      @RequestParam(value = "size") Integer size) {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                groupService.getAllBySemester(semesterId, page, size)), HttpStatus.OK);
    }
}
