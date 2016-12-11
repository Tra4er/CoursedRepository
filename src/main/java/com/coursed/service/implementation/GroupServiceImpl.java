package com.coursed.service.implementation;

import com.coursed.model.Group;
import com.coursed.model.Semester;
import com.coursed.model.Speciality;
import com.coursed.repository.GroupRepository;
import com.coursed.repository.SemesterRepository;
import com.coursed.repository.SpecialityRepository;
import com.coursed.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Hexray on 26.11.2016.
 */
@Service
public class GroupServiceImpl implements GroupService {
//    TODO LOGGER

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private SpecialityRepository specialityRepository;

    @Autowired
    private SemesterRepository semesterRepository;

    @Override
    public void create(Group group) {
        groupRepository.save(group);
    }

    @Override
    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    @Override
    public List<Group> findAllFromSpeciality(Long specialityId) {
        Speciality speciality = specialityRepository.findOne(specialityId);
        if(speciality != null)
        {
            return speciality.getGroups();
        }

        return null; // TODO throw new IllegalArgumentException()
    }

    @Override
    public List<Group> findAllFromSemester(Long semesterId) {
        Semester semester = semesterRepository.findOne(semesterId);
        if(semester != null)
        {
            return semester.getGroups();
        }

        return null;
    }

    @Override
    public List<Group> findAllFromSpecialityAndSemester(Long specialityId, Long semesterId) {
        Speciality speciality = specialityRepository.findOne(specialityId);
        Semester semester = semesterRepository.findOne(semesterId);
        //Intersection
        if(semester != null && speciality != null)
        {
            return semester.getGroups().stream().filter(speciality.getGroups()::contains).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<Group> findAllWithoutCurator(Long semesterId) {
        return null;
    }
}
