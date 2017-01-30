package com.coursed.controller.rest;

import com.coursed.dto.DisciplineDTO;
import com.coursed.model.Discipline;
import com.coursed.model.auth.User;
import com.coursed.service.DisciplineService;
import com.coursed.service.UserService;
import com.coursed.util.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;

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

    @GetMapping
    public ResponseEntity<GenericResponse> get() {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                disciplineService.findAll()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GenericResponse> post(@RequestBody DisciplineDTO disciplineDTO) {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                disciplineService.create(disciplineDTO)), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<GenericResponse> search() {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.NO_CONTENT.value(), "success"), HttpStatus.NO_CONTENT);
    }

    // OLD

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

        User user = userService.getByEmail(principal.getName());

        return disciplineService.getAllActualConnectedWithTeacher(user.getTeacherEntity().getId(), plannedEventId);
    }

    @GetMapping("/getAllDisciplinesFromPlannedEvent")
    private Collection<Discipline> getAllDisciplinesFromPlannedEvent(@RequestParam(name = "plannedEventId")Long plannedEventId,
                                                                     @RequestParam(name = "groupId")Long groupId)
    {
        return disciplineService.getAllDisciplinesFromPlannedEvent(plannedEventId, groupId);
    }
}
