package com.coursed.controller.rest;

import com.coursed.model.Speciality;
import com.coursed.model.Year;
import com.coursed.service.SpecialityService;
import com.coursed.service.YearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Created by Hexray on 01.12.2016.
 */
@RestController
public class SpecialityResourse {
    @Autowired
    private SpecialityService specialityService;

    @RequestMapping(value = "/api/specialities/getAll", method = RequestMethod.GET)
    private Collection<Speciality> getSpecialities() {
        return specialityService.findAll();
    }

    @RequestMapping(value = "/api/specialities/create", method = RequestMethod.POST)
    private void createSpeciality(@RequestBody Speciality speciality) {
        specialityService.create(speciality);
    }
}