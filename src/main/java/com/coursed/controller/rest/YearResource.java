package com.coursed.controller.rest;

import com.coursed.model.Year;
import com.coursed.service.YearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Created by Hexray on 16.11.2016.
 */
@RestController
@RequestMapping("/api/years")
public class YearResource {
    @Autowired
    private YearService yearService;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    private Collection<Year> getYears() {
        return yearService.findAll();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    private void addYear(@RequestBody Year newYear) {
        int a = 5;
        yearService.create(newYear);
    }

}
