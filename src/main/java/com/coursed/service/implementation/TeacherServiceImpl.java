package com.coursed.service.implementation;

import com.coursed.model.Discipline;
import com.coursed.model.Group;
import com.coursed.model.Teacher;
import com.coursed.model.auth.Role;
import com.coursed.model.auth.User;
import com.coursed.repository.DisciplineRepository;
import com.coursed.repository.GroupRepository;
import com.coursed.repository.RoleRepository;
import com.coursed.repository.TeacherRepository;
import com.coursed.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Hexray on 06.12.2016.
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Override
    public void create(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    @Override
    public Teacher getById(Long id) {
        return teacherRepository.findOne(id);
    }

    @Override
    public List<Teacher> getAll() {
        return teacherRepository.findAll();
    }

    @Override
    public List<Teacher> getAllCuratorsOfGroup(Long groupId) {
        Group group = groupRepository.findOne(groupId);

        return teacherRepository.findAll().stream()
                .filter(teacher -> !teacher.getDisciplines().contains(group))
                .collect(Collectors.toList());
    }

    @Override
    public void setAsCurator(Long teacherId, Long groupId) {
        Teacher teacher = teacherRepository.findOne(teacherId);
        Group group = groupRepository.findOne(groupId);

        List<Teacher> curators = group.getCurators();
        curators.add(teacher);

        group.setCurators(curators);
        groupRepository.save(group);
    }

    @Override
    public List<Teacher> getAllTeachersWithoutDiscipline(Long disciplineId) {
        Role role = roleRepository.findByName("ROLE_TEACHER");

        Discipline discipline = disciplineRepository.findOne(disciplineId);

        return teacherRepository.findAll().stream()
                .filter(teacher -> !teacher.getDisciplines().contains(discipline))
                .collect(Collectors.toList());
    }

    @Override
    public List<Teacher> getAllTeachersWithDiscipline(Long disciplineId) {
        Role role = roleRepository.findByName("ROLE_TEACHER");

        Discipline discipline = disciplineRepository.findOne(disciplineId);

        return teacherRepository.findAll().stream()
                .filter(teacher -> teacher.getDisciplines().contains(discipline))
                .collect(Collectors.toList());
    }
}
