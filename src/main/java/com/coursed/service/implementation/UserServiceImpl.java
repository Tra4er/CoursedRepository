package com.coursed.service.implementation;

import com.coursed.dto.*;
import com.coursed.model.Group;
import com.coursed.model.Student;
import com.coursed.model.Teacher;
import com.coursed.model.auth.Role;
import com.coursed.model.auth.User;
import com.coursed.model.auth.VerificationToken;
import com.coursed.repository.*;
import com.coursed.security.error.UserAlreadyExistException;
import com.coursed.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Override
    public User registerStudent(UserStudentDTO registrationForm) {
        if (emailExist(registrationForm.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: " + registrationForm.getEmail());
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
        return userRepository.save(user);
    }

    @Override
    public User registerTeacher(UserTeacherDTO registrationForm) { // TODO move to teacherService
        if (emailExist(registrationForm.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: " + registrationForm.getEmail());
        }

        User user = new User();
        user.setEmail(registrationForm.getEmail());
        user.setPassword(registrationForm.getPassword());
        user.setAsTeacher(true);
        user.setAsStudent(false);
        user.setEnabled(false);
        user.setRegistrationDate(new Date());
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        Teacher teacher = new Teacher();
        teacher.setFirstName(registrationForm.getFirstName());
        teacher.setLastName(registrationForm.getLastName());
        teacher.setPatronymic(registrationForm.getPatronymic());
        teacher.setPhoneNumber(registrationForm.getPhoneNumber());

        user.setTeacherEntity(teacher);

        Set<Role> roles = new HashSet<>();
        Role registeredRole = roleRepository.findByName("ROLE_REGISTERED");

        if (registeredRole == null) {
            LOGGER.error("There is no role with name 'ROLE_REGISTERED' to create the association with user");
            throw new RuntimeException("There is no role with name 'ROLE_REGISTERED' to create the association with user. You have to add base info");
        }
        roles.add(registeredRole);

        user.setRoles(roles);

        LOGGER.debug("Saving user with email={}", user.getEmail().replaceFirst("@.*", "@***"));
        return userRepository.save(user);
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
    public List<User> findAllUnconfirmedTeachers() {
        Role role = roleRepository.findByName("ROLE_TEACHER");
        return findAll().stream()
                .filter(User::isATeacher)
                .filter(user -> !(user.getRoles().contains(role)))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findAllTeachers(Long groupId) {
        Role role = roleRepository.findByName("ROLE_TEACHER");


        if (groupId == null) {
            return findAll().stream()
                    .filter(User::isATeacher)
                    .filter(user -> user.getRoles().contains(role))
                    .collect(Collectors.toList());
        } else {
            Group exceptThisGroup = groupRepository.findOne(groupId);
            return findAll().stream()
                    .filter(User::isATeacher)
                    .filter(user -> user.getRoles().contains(role))
                    .filter(user -> !(user.getTeacherEntity().getCuratedGroups().contains(exceptThisGroup)))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<User> findAllGroupCurators(Long groupId) {
        List<Teacher> teachers = groupRepository.findOne(groupId).getCurators();
        List<User> users = new ArrayList<>();

        for (Teacher teacher : teachers) {
            users.add(userRepository.findOne(teacher.getUser().getId()));
        }
        return users;
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.delete(userId);
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
    public void makeATeacher(Long userId) {
        Role role = roleRepository.findByName("ROLE_TEACHER");
        if (role != null) {
            connectUserWithRole(userId, role.getId());
        } else {
            throw new RuntimeException("There no base role ROLE_TEACHER");
        }

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

//    NON API

    private boolean emailExist(String email) {
        return userRepository.findOneByEmail(email).isPresent();
    }
}
