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
    Page<GroupDTO> getAll(int page, int size);
    List<Group> findAllFromSpeciality(Long specialityId);
    List<Group> findAllFromSemester(Long semesterId);
    List<Group> findAllFromSpecialityAndSemester(Long specialityId, Long semesterId);
    void addCurator(Long groupId, Long teacherId);
    List<TeacherDTO.TeacherTitleDTO> getCurators(Long groupId);
    void addStudent(Long groupId, Long studentId);
    Page<StudentDTO.StudentTitleDTO> getStudents(Long groupId, int page, int size);
    List<Group> findAllWithoutCurator(Long semesterId);
    List<Group> findAllForGrading(Long educationPlanId, SemesterNumber semesterNumber, CourseNumber courseNumber);
    List<Group> findAllByPlannedEvent(Long plannedEventId);
    Group findOne(Long groupId);
}
