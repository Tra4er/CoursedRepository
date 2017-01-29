package com.coursed.service.implementation;

import com.coursed.error.exception.PageSizeTooBigException;
import com.coursed.model.Group;
import com.coursed.model.Student;
import com.coursed.repository.GroupRepository;
import com.coursed.repository.RoleRepository;
import com.coursed.repository.StudentRepository;
import com.coursed.repository.UserRepository;
import com.coursed.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Trach on 12/12/2016.
 */
@Service
public class StudentServiceImpl implements StudentService {

    public static final int DEFAULT_PAGE = 1;
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int MAX_PAGE_SIZE = 20;

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
    public Student getById(Long id) {
        return studentRepository.findOne(id);
    }

    @Override
    public List<Student> getAll() {
        return studentRepository.findAll(new PageRequest(DEFAULT_PAGE, DEFAULT_PAGE_SIZE));
    }

    @Override
    public List<Student> getAll(int page, int size) {
        if(size > MAX_PAGE_SIZE) {
            throw new PageSizeTooBigException("Requested size is too big.");
        }
        return studentRepository.findAll(new PageRequest(page, size));
    }

    @Override
    public List<Student> getAllFromGroup(Long groupId) {
        Group group = groupRepository.findOne(groupId);

        return group.getStudents();
    }
}
