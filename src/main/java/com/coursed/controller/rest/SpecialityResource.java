package com.coursed.controller.rest;

import com.coursed.dto.SpecialityDTO;
import com.coursed.model.Speciality;
import com.coursed.service.GroupService;
import com.coursed.service.SpecialityService;
import com.coursed.util.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Created by Hexray on 01.12.2016.
 */
@RestController
@RequestMapping("/api/specialities")
public class SpecialityResource {

    @Autowired
    private SpecialityService specialityService;

    @Autowired
    private GroupService groupService;

    @GetMapping
    public ResponseEntity<GenericResponse> get(@RequestParam(value = "page") Integer page,
                                               @RequestParam(value = "size") Integer size) {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                specialityService.getAll(page, size)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GenericResponse> post(@RequestBody SpecialityDTO specialityDTO) {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.CREATED.value(), "success",
                specialityService.create(specialityDTO)), HttpStatus.CREATED);
    }

    @GetMapping("{specialityId}")
    public ResponseEntity<GenericResponse> getById(@PathVariable("specialityId") Long specialityId) {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                specialityService.getById(specialityId)), HttpStatus.OK);
    }

    @GetMapping("{specialityId}/groups")
    public ResponseEntity<GenericResponse> getGroups(@PathVariable("specialityId") Long specialityId,
                                                     @RequestParam(value = "page") Integer page,
                                                     @RequestParam(value = "size") Integer size) {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                groupService.getAllBySpeciality(specialityId, page, size)), HttpStatus.OK);
    }

}