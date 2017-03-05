package com.coursed.service.implementation;

import com.coursed.dto.TeacherDTO;
import com.coursed.error.exception.PageSizeTooBigException;
import com.coursed.model.Group;
import com.coursed.model.Teacher;
import com.coursed.model.auth.Role;
import com.coursed.repository.DisciplineRepository;
import com.coursed.repository.GroupRepository;
import com.coursed.repository.RoleRepository;
import com.coursed.repository.TeacherRepository;
import com.coursed.service.TeacherService;
import com.coursed.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Hexray on 06.12.2016.
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    public static final int DEFAULT_PAGE = 1;
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int MAX_PAGE_SIZE = 20;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Autowired
    private UserService userService;

    @Override
    public void create(Teacher teacher) { // TODO change to save and check if teacher exist
        teacherRepository.save(teacher);
    }

    @Override
    public void delete(Long teacherId) {
        Teacher teacher = teacherRepository.findOne(teacherId);
        userService.deleteUser(teacher.getUser().getId());
    }

    @Override
    public TeacherDTO getById(Long id) {
        return teacherRepository.findOneInDTO(id);
    }

    @Override
    public Page<TeacherDTO.TeacherTitleDTO> getAll() {
        return teacherRepository.findAllInDTO(new PageRequest(DEFAULT_PAGE, MAX_PAGE_SIZE));
    }

    @Override
    public Page<TeacherDTO.TeacherTitleDTO> getAll(int page, int size) {
        if(size > MAX_PAGE_SIZE) {
            throw new PageSizeTooBigException("Requested size is too big.");
        }
        return teacherRepository.findAllInDTO(new PageRequest(page, size));
    }

    @Override
    public Page<TeacherDTO.TeacherTitleDTO> getAllUnconfirmed(int page, int size) {
        if(size > MAX_PAGE_SIZE) {
            throw new PageSizeTooBigException("Requested size is too big.");
        }
        return teacherRepository.findAllUnconfirmedInDTO(new PageRequest(page, size));
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
    public void confirmTeacher(Long teacherId) {
        Role role = roleRepository.findByName("ROLE_TEACHER");
        Teacher teacher = teacherRepository.findOne(teacherId);
        if (role != null) {
            userService.connectUserWithRole(teacher.getUser(), role);
        } else {
            throw new RuntimeException("There no base role ROLE_TEACHER");
        }

    }

    @Override
    public Page<TeacherDTO.TeacherTitleDTO> getAllWithoutDiscipline(Long disciplineId, int page, int size) {
        return teacherRepository.findAllWithoutDisciplineInDTO(disciplineId, new PageRequest(page,size));
    }

    @Override
    public Page<TeacherDTO.TeacherTitleDTO> getAllByDiscipline(Long disciplineId, int page, int size) {
        return teacherRepository.findAllByDisciplineInDTO(disciplineId, new PageRequest(page,size));
    }

    @Override
    public Page<TeacherDTO.TeacherTitleDTO> getAllCuratorsByGroup(Long groupId, int page, int size) {
        return teacherRepository.findAllCuratorsByGroupInDTO(groupId, new PageRequest(page, size));
    }

    @Override
    public Page<TeacherDTO.TeacherTitleDTO> getAllNotCuratorsByGroup(Long groupId, int page, int size) {
        return teacherRepository.findAllNotCuratorsByGroupInDTO(groupId, new PageRequest(page, size));
    }
}
