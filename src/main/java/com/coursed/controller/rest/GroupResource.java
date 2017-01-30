package com.coursed.controller.rest;

import com.coursed.dto.GroupDTO;
import com.coursed.model.*;
import com.coursed.model.enums.CourseNumber;
import com.coursed.model.enums.SemesterNumber;
import com.coursed.service.GroupService;
import com.coursed.service.SemesterService;
import com.coursed.service.SpecialityService;
import com.coursed.service.TeacherService;
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

    @GetMapping
    private ResponseEntity<GenericResponse> get(@RequestParam(value = "page", required = false) Integer page,
                                                @RequestParam(value = "size", required = false) Integer size) {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                groupService.findAll()), HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<GenericResponse> post(@RequestBody GroupDTO groupDTO) {
        Semester sem = semesterService.findOne(groupDTO.getSemesterId());
        Speciality spec = specialityService.findOne(groupDTO.getSpecialityId());

        Group group = new Group(groupDTO.getNumber(), groupDTO.getGroupType(), groupDTO.getGroupDegree(),
                groupDTO.getCourseNumber(),sem, spec);
        return new ResponseEntity<>(new GenericResponse(HttpStatus.CREATED.value(), "success",
                groupService.create(group)), HttpStatus.CREATED);
    }

    @GetMapping("/search")
    private ResponseEntity<GenericResponse> search(@RequestParam(value = "page", required = false) Integer page,
                                                   @RequestParam(value = "size", required = false) Integer size,
                                                   @RequestParam(value = "filter", required = false) String filter,
                                                   @RequestParam(name = "semesterNumber") SemesterNumber semesterNumber,
                                                   @RequestParam(name = "courseNumber") CourseNumber courseNumber,
                                                   @RequestParam(name = "disciplineId") Long disciplineId,
                                                   @RequestParam(name = "specialityId", required = false) Long specialityId,
                                                   @RequestParam(name = "semesterId", required = false) Long semesterId) {

        switch (filter) {
            case "withoutCurators" : {
                return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                        groupService.findAll()), HttpStatus.OK); // TODO findAll()
            }
            case "forGrading" : {
                if (disciplineId != null && semesterId != null && courseNumber != null) {
                    return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                            groupService.findAllForGrading(disciplineId, semesterNumber, courseNumber)), HttpStatus.OK);
                } else {
                    throw new IllegalArgumentException("Missing parameters.");
                }
            }
        }
        if(specialityId != null && semesterId != null)
        {
            return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                    groupService.findAllFromSpecialityAndSemester(specialityId, semesterId)), HttpStatus.OK);
        }
        else if(specialityId != null)
        {
            return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                    groupService.findAllFromSpeciality(specialityId)), HttpStatus.OK);
        }
        else if(semesterId != null)
        {
            return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                    groupService.findAllFromSemester(semesterId)), HttpStatus.OK);
        }

        return new ResponseEntity<>(new GenericResponse(HttpStatus.NO_CONTENT.value(), "success"), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    private ResponseEntity<GenericResponse> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                groupService.findOne(id)), HttpStatus.OK);
    }

    @GetMapping("/{groupId}/curators/{teacherId}")
    private ResponseEntity<GenericResponse> getById(@PathVariable("groupId") Long groupId,
                                                    @RequestParam(name = "teacherId") Long teacherId) {
        teacherService.setAsCurator(teacherId, groupId);
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success"), HttpStatus.OK);
    }

    //OLD TODO

    @GetMapping("/getAllFromSemesterFromPlannedEvent")
    private Collection<Group> getGroupsFromSemesterFromPlannedEvent(@RequestParam(name = "plannedEventId")Long plannedEventId) {
        return groupService.findAllFromSemesterByPlannedEvent(plannedEventId);
    }

}
