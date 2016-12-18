package com.coursed.controller.rest;

import com.coursed.dto.GroupCreateForm;
import com.coursed.model.*;
import com.coursed.service.GroupService;
import com.coursed.service.SemesterService;
import com.coursed.service.SpecialityService;
import com.coursed.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * Created by Hexray on 27.11.2016.
 */
@RestController
@RequestMapping("/api/groups") // TODO group(s)?
public class GroupResource {
    @Autowired
    private GroupService groupService;

    @Autowired
    private SemesterService semesterService;

    @Autowired
    private SpecialityService specialityService;

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/getAll")
    private Collection<Group> getGroups(@RequestParam(name = "specialityId", required = false) Long specialityId,
                                        @RequestParam(name = "semesterId", required = false) Long semesterId) {

        if(specialityId != null && semesterId != null)
        {
            return groupService.findAllFromSpecialityAndSemester(specialityId, semesterId);
        }

        if(specialityId != null)
        {
            return groupService.findAllFromSpeciality(specialityId);
        }

        if(semesterId != null)
        {
            return groupService.findAllFromSemester(semesterId);
        }

        return groupService.findAll();
    }

    @GetMapping("/getAllWithoutCurators")
    private Collection<Group> getGroupsWithoutCurators() {
        return groupService.findAll();
    }

    @PostMapping("/create")
    private Group createGroup(@RequestBody GroupCreateForm groupCreateForm) {
        Semester sem = semesterService.findOne(groupCreateForm.getSemesterId());
        Speciality spec = specialityService.findOne(groupCreateForm.getSpecialityId());

        Group group = new Group(groupCreateForm.getNumber(), groupCreateForm.getGroupType(), groupCreateForm.getGroupDegree(),
                groupCreateForm.getCourseNumber(),sem, spec);
        return groupService.create(group);
    }

    @PostMapping("/connectWithTeacher")
    private void setGroupCurator(@RequestParam(name = "groupId") Long groupId, @RequestParam(name = "teacherId") Long teacherId){
        teacherService.setAsCurator(teacherId, groupId);
    }

}
