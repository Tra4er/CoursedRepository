package com.coursed.service;

import com.coursed.dto.GroupDTO;
import com.coursed.dto.StudentDTO;
import com.coursed.dto.TeacherDTO;
import com.coursed.model.Group;
import com.coursed.model.enums.CourseNumber;
import com.coursed.model.enums.SemesterNumber;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Hexray on 26.11.2016.
 */
public interface GroupService {
    Group create(Group group);
    GroupDTO getById(Long groupId);
    Page<GroupDTO> getAll(int page, int size);
    Page<GroupDTO> getAllWithoutCurators(int page, int size);
    Page<GroupDTO> getAllBySpeciality(Long specialityId, int page, int size);
    Page<GroupDTO> getAllBySemester(Long semesterId, int page, int size);
    Page<GroupDTO> getAllByPlannedEvent(Long plannedEventId, int page, int size);
    List<Group> findAllFromSpecialityAndSemester(Long specialityId, Long semesterId);
    void addCurator(Long groupId, Long teacherId);
    void addStudent(Long groupId, Long studentId);
    List<Group> findAllForGrading(Long educationPlanId, SemesterNumber semesterNumber, CourseNumber courseNumber);
    List<Group> findAllByPlannedEvent(Long plannedEventId);
}
