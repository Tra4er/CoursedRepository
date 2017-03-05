package com.coursed.service.implementation;

import com.coursed.dto.StudentDTO;
import com.coursed.dto.UserStudentDTO;
import com.coursed.error.exception.PageSizeTooBigException;
import com.coursed.error.exception.UserAlreadyExistException;
import com.coursed.model.Group;
import com.coursed.model.Student;
import com.coursed.model.auth.Role;
import com.coursed.model.auth.User;
import com.coursed.repository.GroupRepository;
import com.coursed.repository.RoleRepository;
import com.coursed.repository.StudentRepository;
import com.coursed.repository.UserRepository;
import com.coursed.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Trach on 12/12/2016.
 */
@Service
public class StudentServiceImpl implements StudentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);

    public static final int DEFAULT_PAGE = 1;
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int MAX_PAGE_SIZE = 10;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public StudentDTO create(UserStudentDTO registrationForm) {
        if (emailExist(registrationForm.getEmail())) {
            throw new UserAlreadyExistException("Trying to save new account but there is already one with this email.");
        }

        User user = new User();
        user.setEmail(registrationForm.getEmail());
        user.setPassword(registrationForm.getPassword());
        user.setAsTeacher(false);
        user.setAsStudent(true);
        user.setEnabled(false);
        user.setRegistrationDate(new Date());
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        Student student = new Student();
        student.setFirstName(registrationForm.getFirstName());
        student.setLastName(registrationForm.getLastName());
        student.setPatronymic(registrationForm.getPatronymic());
        student.setPhoneNumber(registrationForm.getPhoneNumber());
        student.setAdditionalInformation(registrationForm.getAdditionalInformation());
        student.setGradeBookNumber(registrationForm.getGradeBookNumber());
        student.setAddress(registrationForm.getAddress());
        student.setBirthDate(registrationForm.getBirthDate());

        if ("true".compareTo(registrationForm.getIsBudgetStudent()) == 0)
            student.setBudgetStudent(true);
        else
            student.setBudgetStudent(false);
        student.setParentsInfo(registrationForm.getParentsInfo());
        student.setStudentEducationStatus(registrationForm.getStudentEducationStatus());

        Group group = groupRepository.findOne(registrationForm.getGroupId());
        student.setGroup(group);

        user.setStudentEntity(student);

        Set<Role> roles = new HashSet<>();
        Role registeredRole = roleRepository.findByName("ROLE_REGISTERED");

        if (registeredRole == null) {
            LOGGER.error("There is no role with name 'ROLE_REGISTERED' to create the association with user");
            throw new RuntimeException("There is no role with name 'ROLE_REGISTERED' to create the association with user. You have to add base info");
        }
        roles.add(registeredRole);

        user.setRoles(roles);

        LOGGER.debug("Saving user with email={}", user.getEmail().replaceFirst("@.*", "@***"));

        User saved = userRepository.save(user);

        return studentRepository.findOneInDTO(saved.getStudentEntity().getId());
    }

    @Override
    public StudentDTO getById(Long id) {
        return studentRepository.findOneInDTO(id);
    }

    @Override
    public Page<StudentDTO.StudentTitleDTO> getAll() {
        return studentRepository.findAllInDTO(new PageRequest(DEFAULT_PAGE, MAX_PAGE_SIZE));
    }

    @Override
    public Page<StudentDTO.StudentTitleDTO> getAll(int page, int size) {
        if(size > MAX_PAGE_SIZE) {
            throw new PageSizeTooBigException("Requested size is too big.");
        }
        return studentRepository.findAllInDTO(new PageRequest(page, size));
    }

    @Override
    public Page<StudentDTO.StudentTitleDTO> getAllByGroup(Long groupId, int page, int size) {
        return studentRepository.findAllByGroupInDTO(groupId, new PageRequest(page, size));
    }

    //    NON API

    private boolean emailExist(String email) {
        return userRepository.findOneByEmail(email) != null;
    }
}
