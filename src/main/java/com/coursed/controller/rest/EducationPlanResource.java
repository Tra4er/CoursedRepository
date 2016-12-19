package com.coursed.controller.rest;

import com.coursed.dto.EducationPlanDTO;
import com.coursed.model.EducationPlan;
import com.coursed.service.EducationPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Created by Hexray on 11.12.2016.
 */
@RestController
@RequestMapping("/api/educationPlans")
public class EducationPlanResource {
    @Autowired
    private EducationPlanService educationPlanService;

    @PostMapping("/create")
    private EducationPlan createEducationPlan(@RequestBody EducationPlanDTO planForm) {
        return educationPlanService.create(planForm);
    }

    @GetMapping("/getAll")
    private Collection<EducationPlan> getAll()
    {
        return educationPlanService.findAll();
    }

    @GetMapping("/getOne")
    private EducationPlan getOne(@RequestParam(name = "educationPlanId") Long educationPlanId)
    {
        return educationPlanService.findOne(educationPlanId);
    }
}
