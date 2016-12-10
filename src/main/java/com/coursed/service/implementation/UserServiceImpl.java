package com.coursed.service.implementation;

import com.coursed.dto.StudentRegistrationForm;
import com.coursed.dto.TeacherRegistrationForm;
import com.coursed.dto.UserRegistrationForm;
import com.coursed.model.Group;
import com.coursed.model.Student;
import com.coursed.model.auth.Role;
import com.coursed.model.auth.User;
import com.coursed.model.auth.VerificationToken;
import com.coursed.repository.GroupRepository;
import com.coursed.repository.RoleRepository;
import com.coursed.repository.UserRepository;
import com.coursed.repository.VerificationTokenRepository;
import com.coursed.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Hexray on 06.11.2016.
 * Edited by Trach on 07.11.2016
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Override
    public User registerStudent(UserRegistrationForm userForm, StudentRegistrationForm studentForm) {

        User user = new User();
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setATeacher(false);
        user.setAStudent(true);
        user.setEnabled(false);
        user.setRegistrationDate(new Date());
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        Student student = new Student();
        student.setFirstName(studentForm.getFirstName());
        student.setLastName(studentForm.getLastName());
        student.setPatronymic(studentForm.getPatronymic());
        student.setAdditionalInformation(studentForm.getAdditionalInformation());
        student.setAddress(studentForm.getAddress());
        student.setBirthDate(studentForm.getBirthDate());
        student.setBudgetStudent(studentForm.getBudgetStudent());
        student.setParentsInfo(studentForm.getParentsInfo());
        student.setStudentEducationStatus(studentForm.getStudentEducationStatus());

        Group group = groupRepository.findOne(studentForm.getGroupId());
        student.setGroup(group);

        user.setStudent(student);

        Set<Role> roles = new HashSet<>();
        Role registeredRole = roleRepository.findByName("ROLE_REGISTERED");

        if(registeredRole == null)
        {
            LOGGER.error("There is no role with name 'ROLE_REGISTERED' to create the association with user");
            throw new RuntimeException("There is no role with name 'ROLE_REGISTERED' to create the association with user. You have to add base info");
        }
        roles.add(registeredRole);

        user.setRoles(roles);

        LOGGER.debug("Saving user with email={}", user.getEmail().replaceFirst("@.*", "@***"));
        return userRepository.save(user);
    }

    @Override
    public User registerTeacher(UserRegistrationForm userForm, TeacherRegistrationForm teacherForm) {
        //TODO Implement
        return null;
    }

    @Override
    public void saveRegisteredUser(final User user) {
        LOGGER.debug("Saving registered user: {}", user);
        userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        LOGGER.debug("Getting user={}", id);
        return userRepository.findOne(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        LOGGER.debug("Getting user by email={}", email.replaceFirst("@.*", "@***"));
        return userRepository.findOneByEmail(email);
    }

    @Override
    public List<User> findAll() {
        LOGGER.debug("Getting all users");
        return userRepository.findAll();
    }

    @Override
    public void connectUserWithRole(Long userId, Long roleId) {
        //TODO: log it and check it for existing
        Role role = roleRepository.findOne(roleId);
        User user = userRepository.findOne(userId);
        Set<Role> userRoles = user.getRoles();

        userRoles.add(role);
        user.setRoles(userRoles);

        userRepository.save(user);
    }

    @Override
    public void connectUserWithRole(User user, Role role) {
        //TODO: log it and check it for existing
        Set<Role> userRoles = user.getRoles();

        userRoles.add(role);
        user.setRoles(userRoles);

        userRepository.save(user);
    }

    @Override
    public void createVerificationTokenForUser(final User user, final String token) {
        final VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    @Override
    public VerificationToken getVerificationToken(final String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

}
