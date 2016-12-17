package com.coursed.controller.rest;

import com.coursed.dto.DisciplineForm;
import com.coursed.model.Discipline;
import com.coursed.service.DisciplineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Created by Hexray on 11.12.2016.
 */
@RestController
@RequestMapping("/api/discipline")
public class DisciplineResource {
    @Autowired
    private DisciplineService disciplineService;

    @PostMapping("/create")
    private void createEducationPlan(@RequestBody DisciplineForm disciplineForm) {
        disciplineService.create(disciplineForm);
    }

    @GetMapping("/getAll")
    private Collection<Discipline> getAll()
    {
        return disciplineService.findAll();
    }

    @PostMapping("connectTeacherWithDiscipline")
    private void connectWithTeacher(@RequestParam(name = "disciplineId") Long disciplineId,
                                    @RequestParam(name = "teacherId") Long teacherId)
    {
        disciplineService.connectWithTeacher(disciplineId, teacherId);
    }
}
