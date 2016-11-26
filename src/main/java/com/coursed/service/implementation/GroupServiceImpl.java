package com.coursed.service.implementation;

import com.coursed.model.Group;
import com.coursed.repository.GroupRepository;
import com.coursed.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Hexray on 26.11.2016.
 */
@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Override
    public void create(Group group) {
        groupRepository.save(group);
    }

    @Override
    public List<Group> findAll() {
        return groupRepository.findAll();
    }
}
