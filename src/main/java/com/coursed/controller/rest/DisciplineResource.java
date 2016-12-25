package com.coursed.controller.rest;

import com.coursed.dto.DisciplineDTO;
import com.coursed.model.Discipline;
import com.coursed.model.auth.User;
import com.coursed.service.DisciplineService;
import com.coursed.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by Hexray on 11.12.2016.
 */
@RestController
@RequestMapping("/api/disciplines")
public class DisciplineResource {
    @Autowired
    private DisciplineService disciplineService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    private Discipline createEducationPlan(@RequestBody DisciplineDTO disciplineDTO) {
        return disciplineService.create(disciplineDTO);
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

    @GetMapping("/getAllActualConnectedWithTeacher")
    private Collection<Discipline> getAllActualConnectedWithTeacher(@RequestParam(name = "plannedEventId", required = false) Long plannedEventId, Principal principal)
    {
        if(principal == null)
            throw new IllegalArgumentException("You haven`t logged in to retrieve principal");

        Optional<User> user = userService.getUserByEmail(principal.getName());

        return disciplineService.getAllActualConnectedWithTeacher(user.get().getTeacherEntity().getId(), plannedEventId);
    }

    @GetMapping("/getAllDisciplinesFromPlannedEvent")
    private Collection<Discipline> getAllDisciplinesFromPlannedEvent(@RequestParam(name = "plannedEventId")Long plannedEventId)
    {
        return disciplineService.getAllDisciplinesFromPlannedEvent(plannedEventId);
    }
}
