package com.coursed.service.implementation;

import com.coursed.dto.StudentRegistrationForm;
import com.coursed.dto.UserStudentRegistrationForm;
import com.coursed.model.Student;
import com.coursed.model.auth.Role;
import com.coursed.model.auth.User;
import com.coursed.repository.RoleRepository;
import com.coursed.repository.StudentRepository;
import com.coursed.repository.UserRepository;
import com.coursed.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Trach on 12/12/2016.
 */
public class StudentServiceImpl implements StudentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void create(Student student) {
        studentRepository.save(student);
    }

    @Override
    public User registerStudent(UserStudentRegistrationForm registrationForm) {

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
        student.setAddress(registrationForm.getAddress());
        student.setGradeBookNumber(registrationForm.getGradeBookNumber());
        student.setBirthDate(registrationForm.getBirthDate());
        student.setStudentEducationStatus(registrationForm.getStudentEducationStatus());
        student.setBudgetStudent(registrationForm.getBudgetStudent());
        student.setAdditionalInformation(registrationForm.getAdditionalInformation());
        student.setParentsInfo(registrationForm.getParentsInfo());
//        TODO check!!!!
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
        return userRepository.save(user); // TODO where to save? user or student rep
    }

    @Override
    public Student findOne(Long id) {
        return studentRepository.findOne(id);
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }
}
