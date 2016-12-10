package com.coursed.service;

import com.coursed.model.Group;

import java.util.List;

/**
 * Created by Hexray on 26.11.2016.
 */
public interface GroupService {
    void create(Group group);
    List<Group> findAll();
    List<Group> findAllFromSpeciality(Long specialityId);
    List<Group> findAllFromSemester(Long semesterId);
    List<Group> findAllFromSpecialityAndSemester(Long specialityId, Long semesterId);
    List<Group> findAllWithoutCurator(Long semesterId);
}
