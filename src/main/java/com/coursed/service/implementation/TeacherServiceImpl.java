package com.coursed.service.implementation;

import com.coursed.model.Group;
import com.coursed.model.Teacher;
import com.coursed.repository.GroupRepository;
import com.coursed.repository.TeacherRepository;
import com.coursed.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Hexray on 06.12.2016.
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public void create(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    @Override
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    @Override
    public Teacher findOne(Long id) {
        return findOne(id);
    }

    @Override
    public void setAsCurator(Long teacherId, Long groupId) {
        Teacher teacher = teacherRepository.findOne(teacherId);
        Group group = groupRepository.findOne(groupId);

        List<Teacher> curators = group.getCurators();
        curators.add(teacher);

        group.setCurators(curators);
    }
}
