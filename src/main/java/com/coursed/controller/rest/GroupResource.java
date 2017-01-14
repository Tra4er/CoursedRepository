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
    private ResponseEntity<GenericResponse> get(@RequestParam(name = "specialityId", required = false) Long specialityId,
                                                @RequestParam(name = "semesterId", required = false) Long semesterId) {

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

        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                groupService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    private Collection<Group> getGroups(@RequestParam(name = "specialityId", required = false) Long specialityId,
                                        @RequestParam(name = "semesterId", required = false) Long semesterId) {

        if(specialityId != null && semesterId != null)
        {
            return groupService.findAllFromSpecialityAndSemester(specialityId, semesterId);
        }
        else if(specialityId != null)
        {
            return groupService.findAllFromSpeciality(specialityId);
        }
        else if(semesterId != null)
        {
            return groupService.findAllFromSemester(semesterId);
        }

        return groupService.findAll();
    }

    @GetMapping("/getAllForGrading")
    private Collection<Group> getGroupsForGrading(@RequestParam(name = "semesterNumber") SemesterNumber semesterNumber,
                                                  @RequestParam(name = "courseNumber") CourseNumber courseNumber,
                                                  @RequestParam(name = "disciplineId") Long disciplineId)
    {

        return groupService.findAllForGrading(disciplineId, semesterNumber, courseNumber);
    }

    @GetMapping("/getAllWithoutCurators")
    private Collection<Group> getGroupsWithoutCurators() {
        return groupService.findAll();
    }

    @PostMapping("/create")
    private Group createGroup(@RequestBody GroupDTO groupDTO) {
        Semester sem = semesterService.findOne(groupDTO.getSemesterId());
        Speciality spec = specialityService.findOne(groupDTO.getSpecialityId());

        Group group = new Group(groupDTO.getNumber(), groupDTO.getGroupType(), groupDTO.getGroupDegree(),
                groupDTO.getCourseNumber(),sem, spec);
        return groupService.create(group);
    }

    @PostMapping("/connectWithTeacher")
    private void setGroupCurator(@RequestParam(name = "groupId") Long groupId, @RequestParam(name = "teacherId") Long teacherId){
        teacherService.setAsCurator(teacherId, groupId);
    }

    @GetMapping("/getAllFromSemesterFromPlannedEvent")
    private Collection<Group> getGroupsFromSemesterFromPlannedEvent(@RequestParam(name = "plannedEventId")Long plannedEventId) {
        return groupService.findAllFromSemesterFromPlannedEvent(plannedEventId);
    }

    @GetMapping("/getOne")
    private Group getOne(@RequestParam(name = "groupId")Long groupId) {
        return groupService.findOne(groupId);
    }

}
