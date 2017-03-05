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
        return new ResponseEntity<>(new GenericResponse(disciplineService.getAll(page, size)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GenericResponse> post(@RequestBody DisciplineDTO disciplineDTO) {
        return new ResponseEntity<>(new GenericResponse(disciplineService.create(disciplineDTO)), HttpStatus.OK);
    }

    @GetMapping("{disciplineId}")
    public ResponseEntity<GenericResponse> getById(@PathVariable("disciplineId") Long disciplineId) {
        return new ResponseEntity<>(new GenericResponse(disciplineService.getById(disciplineId)), HttpStatus.OK);
    }

    @GetMapping("{disciplineId}/teachers")
    public ResponseEntity<GenericResponse> getTeachers(@PathVariable("disciplineId") Long disciplineId,
                                                       @RequestParam(value = "page") Integer page,
                                                       @RequestParam(value = "size") Integer size) {
        return new ResponseEntity<>(new GenericResponse(teacherService.getAllByDiscipline(disciplineId, page, size)), HttpStatus.OK);
    }

    @PostMapping("{disciplineId}/teachers/{teacherId}")
    public ResponseEntity<GenericResponse> connectWithTeacher(@PathVariable("disciplineId") Long disciplineId,
                                                              @PathVariable("teacherId") Long teacherId) {
        return new ResponseEntity<>(new GenericResponse(disciplineService.connectWithTeacher(disciplineId, teacherId)), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<GenericResponse> search(@RequestParam(value = "page") Integer page,
                                                  @RequestParam(value = "size") Integer size,
                                                  @RequestParam(value = "plannedEventId", required = false) Long plannedEventId,
                                                  @RequestParam(value = "groupId", required = false) Long groupId) {

        if(plannedEventId != null && groupId != null) { // TODO
            return new ResponseEntity<>(new GenericResponse(disciplineService.getAllDisciplinesFromPlannedEvent(plannedEventId, groupId)), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // OLD

    @GetMapping("/getAllActualConnectedWithTeacher")
    private Collection<Discipline> getAllActualConnectedWithTeacher(@RequestParam(name = "plannedEventId", required = false) Long plannedEventId, Principal principal) {
        if (principal == null)
            throw new IllegalArgumentException("You haven`t logged in to retrieve principal");

        User user = userService.getByEmail(principal.getName());

        return disciplineService.getAllActualConnectedWithTeacher(user.getTeacherEntity().getId(), plannedEventId);
    }

}
