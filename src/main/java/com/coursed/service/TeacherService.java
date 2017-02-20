package com.coursed.service;

import com.coursed.dto.TeacherDTO;
import com.coursed.model.Teacher;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Hexray on 06.12.2016.
 */
public interface TeacherService {
    void create(Teacher teacher);
    void delete(Long teacherId);
    TeacherDTO getById(Long id);
    Page<TeacherDTO.TeacherTitleDTO> getAll();
    Page<TeacherDTO.TeacherTitleDTO> getAll(int page, int size);
    Page<TeacherDTO.TeacherTitleDTO> getAllUnconfirmed(int page, int size);
    void setAsCurator(Long teacherId, Long groupId);
    void confirmTeacher(Long teacherId);
    List<Teacher> getAllTeachersWithoutDiscipline(Long disciplineId);
    List<Teacher> getAllTeachersWithDiscipline(Long disciplineId);
}
