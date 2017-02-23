package com.coursed.service;

import com.coursed.dto.StudentDTO;
import com.coursed.dto.UserStudentDTO;
import com.coursed.model.Student;
import com.coursed.model.auth.User;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Trach on 12/12/2016.
 */
public interface StudentService {
    void create(Student student);
    StudentDTO getById(Long id);
    Page<StudentDTO.StudentTitleDTO> getAll();
    Page<StudentDTO.StudentTitleDTO> getAll(int page, int size);
    Page<StudentDTO.StudentTitleDTO> getAllByGroup(Long groupId, int page, int size);
}
