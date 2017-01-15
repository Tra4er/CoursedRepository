package com.coursed.service;

import com.coursed.dto.UserStudentDTO;
import com.coursed.model.Student;
import com.coursed.model.auth.User;

import java.util.List;

/**
 * Created by Trach on 12/12/2016.
 */
public interface StudentService {
    void create(Student student);
    Student getById(Long id);
    List<Student> getAll();
    List<Student> getAllFromGroup(Long groupId);
}
