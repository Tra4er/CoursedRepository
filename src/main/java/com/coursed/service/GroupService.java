package com.coursed.service;

import com.coursed.model.Group;
import com.coursed.model.enums.CourseNumber;
import com.coursed.model.enums.SemesterNumber;

import java.util.List;

/**
 * Created by Hexray on 26.11.2016.
 */
public interface GroupService {
    Group create(Group group);
    List<Group> findAll();
    List<Group> findAllFromSpeciality(Long specialityId);
    List<Group> findAllFromSemester(Long semesterId);
    List<Group> findAllFromSpecialityAndSemester(Long specialityId, Long semesterId);
    List<Group> findAllWithoutCurator(Long semesterId);
    List<Group> findAllForGrading(Long educationPlanId, SemesterNumber semesterNumber, CourseNumber courseNumber);
    List<Group> findAllFromSemesterByPlannedEvent(Long plannedEventId);
    Group findOne(Long groupId);
}
