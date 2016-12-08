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
@RequestMapping
public class GroupResource {
    @Autowired
    private GroupService groupService;

    @Autowired
    private SemesterService semesterService;

    @Autowired
    private SpecialityService specialityService;

    @Autowired
    private TeacherService teacherService;

    @RequestMapping(value = "/api/groups/getAll", method = RequestMethod.GET)
    private Collection<Group> getGroups(@RequestParam(name = "specialityId", required = false) Long specialityId) {

        if(specialityId != null)
        {
            return groupService.findAllFromSpeciality(specialityId);
        }

        return groupService.findAll();
    }

    @RequestMapping(value = "/api/groups/create", method = RequestMethod.POST)
    private void createGroup(@RequestBody GroupCreateForm groupCreateForm) {
        Semester sem = semesterService.findOne(groupCreateForm.getSemesterId());
        Speciality spec = specialityService.findOne(groupCreateForm.getSpecialityId());

        Group group = new Group(groupCreateForm.getNumber(), groupCreateForm.getGroupType(), groupCreateForm.getGroupDegree(),
                groupCreateForm.getCourseNumber(),sem, spec);
        groupService.create(group);
    }

    @RequestMapping(value = "/api/groups/connectWithTeacher", method = RequestMethod.POST)
    private void setGroupCurator(@RequestParam(name = "groupId") Long groupId, @RequestParam(name = "teacherId") Long teacherId){
        teacherService.setAsCurator(teacherId, groupId);
    }

}
