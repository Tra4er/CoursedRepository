package com.coursed.controller.rest;

import com.coursed.model.Group;
import com.coursed.model.Year;
import com.coursed.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

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
        if(specialityId != null)
        {
            return groupService.findAllFromSpeciality(specialityId);
        }

        return groupService.findAll();
    }
}
