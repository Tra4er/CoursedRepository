package com.coursed.controller.rest;

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
@RestController()
public class YearResource {
    @Autowired
    private YearService yearService;

    @RequestMapping(value = "/api/years/getAll", method = RequestMethod.GET)
    private Collection<Year> getYears() {
        return yearService.findAll();
    }

    @RequestMapping(value = "/api/years/create", method = RequestMethod.POST)
    private void createYear(@RequestBody Year year) {
        yearService.create(year);
    }
}