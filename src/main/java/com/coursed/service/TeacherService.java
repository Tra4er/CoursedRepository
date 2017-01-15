package com.coursed.service;

import com.coursed.model.Teacher;

import java.util.List;

/**
 * Created by Hexray on 06.12.2016.
 */
public interface TeacherService {
    void create(Teacher teacher);
    Teacher findOne(Long id);
    List<Teacher> findAll();
    List<Teacher> findAll(Long groupId);
    void setAsCurator(Long teacherId, Long groupId);
    List<Teacher> findAllTeachersWithoutDiscipline(Long disciplineId);
    List<Teacher> findAllTeachersWithDiscipline(Long disciplineId);
}
