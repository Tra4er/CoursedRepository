package com.coursed.controller.rest;

import com.coursed.dto.GroupDTO;
import com.coursed.model.*;
import com.coursed.model.enums.CourseNumber;
import com.coursed.model.enums.SemesterNumber;
import com.coursed.service.*;
import com.coursed.util.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Created by Hexray on 27.11.2016.
 */
@RestController
@RequestMapping("/api/groups")
public class GroupResource {

    @Autowired
    private GroupService groupService;

    @Autowired
    private SemesterService semesterService;

    @Autowired
    private SpecialityService specialityService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @GetMapping
    private ResponseEntity<GenericResponse> get(@RequestParam(value = "page") Integer page,
                                                @RequestParam(value = "size") Integer size) {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                groupService.getAll(page, size)), HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<GenericResponse> post(@RequestBody GroupDTO groupDTO) { // TODO
//        Semester sem = semesterService.getById(groupDTO.getSemesterId());
//        Speciality spec = specialityService.getById(groupDTO.getSpecialityId());
//
//        Group group = new Group(groupDTO.getNumber(), groupDTO.getGroupType(), groupDTO.getGroupDegree(),
//                groupDTO.getCourseNumber(),sem, spec);
//        return new ResponseEntity<>(new GenericResponse(HttpStatus.CREATED.value(), "success",
//                groupService.create(group)), HttpStatus.CREATED);
        return null;
    }

    @GetMapping("/search")
    private ResponseEntity<GenericResponse> search(@RequestParam(value = "page", required = false) Integer page,
                                                   @RequestParam(value = "size", required = false) Integer size,
                                                   @RequestParam(value = "filter", required = false) String filter,
                                                   @RequestParam(name = "semesterNumber", required = false) SemesterNumber semesterNumber,
                                                   @RequestParam(name = "courseNumber", required = false) CourseNumber courseNumber,
                                                   @RequestParam(name = "disciplineId", required = false) Long disciplineId,
                                                   @RequestParam(name = "specialityId", required = false) Long specialityId,
                                                   @RequestParam(name = "semesterId", required = false) Long semesterId) {
        if(filter != null) {
            switch (filter) {
                case "withoutCurators": {
                    return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                            groupService.getAllWithoutCurators(page, size)), HttpStatus.OK);
                }
                case "forGrading": {
                    if (disciplineId != null && semesterId != null && courseNumber != null) {
                        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                                groupService.findAllForGrading(disciplineId, semesterNumber, courseNumber)), HttpStatus.OK);
                    } else {
                        throw new IllegalArgumentException("Missing parameters.");
                    }
                }
                default : break;
            }
        }
        if(specialityId != null && semesterId != null) {
            return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                    groupService.findAllFromSpecialityAndSemester(specialityId, semesterId)), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{groupId}")
    private ResponseEntity<GenericResponse> getById(@PathVariable("groupId") Long groupId) {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                groupService.getById(groupId)), HttpStatus.OK);
    }

    @GetMapping("/{groupId}/curators")
    private ResponseEntity<GenericResponse> getCurators(@PathVariable("groupId") Long groupId,
                                                        @RequestParam(value = "page") Integer page,
                                                        @RequestParam(value = "size") Integer size) {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                teacherService.getAllCuratorsByGroup(groupId, page, size)), HttpStatus.OK);
    }

    @PostMapping("/{groupId}/curators/{teacherId}")
    private ResponseEntity<GenericResponse> setCurator(@PathVariable("groupId") Long groupId,
                                                    @RequestParam(name = "teacherId") Long teacherId) {
        groupService.addCurator(teacherId, groupId);
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success"), HttpStatus.OK);
    }

    @GetMapping("/{groupId}/students")
    private ResponseEntity<GenericResponse> getStudents(@PathVariable("groupId") Long groupId,
                                                        @RequestParam(value = "page") Integer page,
                                                        @RequestParam(value = "size") Integer size) {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                studentService.getAllByGroup(groupId, page, size)), HttpStatus.OK);
    }

    @PostMapping("/{groupId}/students/{studentId}")
    private ResponseEntity<GenericResponse> addStudent(@PathVariable("groupId") Long groupId,
                                                       @RequestParam(name = "studentId") Long studentId) {
        groupService.addStudent(groupId, studentId);
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success"), HttpStatus.OK);
    }
}
