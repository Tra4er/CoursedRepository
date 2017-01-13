package com.coursed.controller.rest;

import com.coursed.dto.SpecialityDTO;
import com.coursed.model.Speciality;
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
public class SpecialityResourse {
    @Autowired
    private SpecialityService specialityService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<GenericResponse> get() {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                specialityService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    private Collection<Speciality> getSpecialities() {
        return specialityService.findAll();
    }

    @PostMapping("/create")
    private Speciality createSpeciality(@RequestBody SpecialityDTO specialityDTO) {
        return specialityService.create(specialityDTO);
    }
}