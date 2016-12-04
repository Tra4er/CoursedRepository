package com.coursed.controller.rest;

import com.coursed.model.Group;
import com.coursed.model.Speciality;
import com.coursed.model.Year;
import com.coursed.service.GroupService;
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

    //TODO: test param
    @RequestMapping(value = "/api/groups/getAll", method = RequestMethod.GET)
    private Collection<Group> getGroups(@RequestParam(name = "specialityId", required = false) Long specialityId) {
        int a = 5;
        if(specialityId != null)
        {
            return groupService.findAllFromSpeciality(specialityId);
        }

        List<Group> groupList = groupService.findAll();

        Speciality spec = groupList.get(1).getSpeciality();

        return groupList;
    }


    @RequestMapping(value = "/api/groups/create", method = RequestMethod.POST)
    private void createGroup(@RequestBody Group group) {
        groupService.create(group);
    }
}
