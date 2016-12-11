package com.coursed.controller.rest;

import com.coursed.dto.EducationPlanForm;
import com.coursed.model.EducationPlan;
import com.coursed.service.EducationPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Created by Hexray on 11.12.2016.
 */
@RestController
@RequestMapping("/api/educationPlan")
public class EducationPlanResource {
    @Autowired
    private EducationPlanService educationPlanService;

    @PostMapping("/create")
    private void createEducationPlan(@RequestBody EducationPlanForm planForm) {
        educationPlanService.create(planForm);
    }

    @GetMapping("/getAll")
    private Collection<EducationPlan> getAll()
    {
        return educationPlanService.findAll();
    }
}
