package com.coursed.controller.rest;

import com.coursed.dto.DisciplineDTO;
import com.coursed.model.Discipline;
import com.coursed.model.auth.User;
import com.coursed.service.DisciplineService;
import com.coursed.service.TeacherService;
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
    private TeacherService teacherService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<GenericResponse> get(@RequestParam(value = "page") Integer page,
                                               @RequestParam(value = "size") Integer size) {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                disciplineService.getAll(page, size)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GenericResponse> post(@RequestBody DisciplineDTO disciplineDTO) {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                disciplineService.create(disciplineDTO)), HttpStatus.OK);
    }

    @GetMapping("{disciplineId}")
    public ResponseEntity<GenericResponse> getById(@PathVariable("disciplineId") Long disciplineId) {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                disciplineService.getById(disciplineId)), HttpStatus.OK);
    }

    @GetMapping("{disciplineId}/teachers")
    public ResponseEntity<GenericResponse> getTeachers(@PathVariable("disciplineId") Long disciplineId,
                                                       @RequestParam(value = "page") Integer page,
                                                       @RequestParam(value = "size") Integer size) {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                teacherService.getAllByDiscipline(disciplineId, page, size)), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<GenericResponse> search() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
