package com.coursed.service.implementation;

import com.coursed.dto.UserStudentDTO;
import com.coursed.model.Group;
import com.coursed.model.Student;
import com.coursed.model.auth.User;
import com.coursed.repository.GroupRepository;
import com.coursed.repository.RoleRepository;
import com.coursed.repository.StudentRepository;
import com.coursed.repository.UserRepository;
import com.coursed.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Trach on 12/12/2016.
 */
@Service
public class StudentServiceImpl implements StudentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public void create(Student student) {
        studentRepository.save(student);
    }

    @Override
    public Student findOne(Long id) {
        return studentRepository.findOne(id);
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public List<Student> findAllFromGroup(Long groupId) {
        Group group = groupRepository.findOne(groupId);

        return group.getStudents();
    }
}
