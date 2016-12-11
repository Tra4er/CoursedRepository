package com.coursed.controller.rest;

import com.coursed.dto.GroupCreateForm;
import com.coursed.model.Group;
import com.coursed.model.Semester;
import com.coursed.model.Speciality;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Hexray on 11.12.2016.
 */
@RestController
public class EducationPlanResource {
    @PostMapping("/create")
    private void createEducationPlan() {

    }
}
