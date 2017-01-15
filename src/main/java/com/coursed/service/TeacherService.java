package com.coursed.service;

import com.coursed.model.Teacher;

import java.util.List;

/**
 * Created by Hexray on 06.12.2016.
 */
public interface TeacherService {
    void create(Teacher teacher);
    Teacher getById(Long id);
    List<Teacher> getAll();
    List<Teacher> getAllCuratorsOfGroup(Long groupId);
    void setAsCurator(Long teacherId, Long groupId);
    List<Teacher> getAllTeachersWithoutDiscipline(Long disciplineId);
    List<Teacher> getAllTeachersWithDiscipline(Long disciplineId);
}
