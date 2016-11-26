package com.coursed.service;

import com.coursed.model.Group;

import java.util.List;

/**
 * Created by Hexray on 26.11.2016.
 */
public interface GroupService {
    void create(Group group);
    List<Group> findAll();
}
