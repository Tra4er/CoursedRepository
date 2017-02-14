package com.coursed.service.implementation;

import com.coursed.dto.*;
import com.coursed.model.Group;
import com.coursed.model.Student;
import com.coursed.model.Teacher;
import com.coursed.model.auth.PasswordResetToken;
import com.coursed.model.auth.Role;
import com.coursed.model.auth.User;
import com.coursed.model.auth.VerificationToken;
import com.coursed.repository.*;
import com.coursed.error.exception.UserAlreadyExistException;
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
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Override
    public User registerStudent(UserStudentDTO registrationForm) {
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
        return userRepository.save(user);
    }

    @Override
    public User registerTeacher(UserTeacherDTO registrationForm) {
        if (emailExist(registrationForm.getEmail())) {
            throw new UserAlreadyExistException("Trying to save new account but there is already one with this email.");
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
    public User getById(Long id) {
        LOGGER.debug("Getting user={}", id);
        return userRepository.findOne(id);
    }

    @Override
    public User getByEmail(String email) {
        LOGGER.debug("Getting user by email={}", email.replaceFirst("@.*", "@***"));
        return userRepository.findOneByEmail(email);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean checkIfUserExists(String email) {
        return userRepository.findOneByEmail(email) == null ? false : true;
    }

    @Override
    public List<User> getAllUnconfirmedTeachers() {
//        System.out.println(userRepository.findM());
        return userRepository.findAllUnconfirmedTeachers();
    }

    @Override
    public List<User> getAllTeachers(Long groupId) {
        Role role = roleRepository.findByName("ROLE_TEACHER");


        if (groupId == null) {
            return userRepository.findAll().stream()
                    .filter(User::isATeacher)
                    .filter(user -> user.getRoles().contains(role))
                    .collect(Collectors.toList());
        } else {
            Group exceptThisGroup = groupRepository.findOne(groupId);
            return userRepository.findAll().stream()
                    .filter(User::isATeacher)
                    .filter(user -> user.getRoles().contains(role))
                    .filter(user -> !(user.getTeacherEntity().getCuratedGroups().contains(exceptThisGroup)))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<User> getAllGroupCurators(Long groupId) {
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
    public void createVerificationTokenForUser(User user, String token) { // TODO
        final VerificationToken myToken = new VerificationToken(token, user);
        verificationTokenRepository.save(myToken);
    }

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return verificationTokenRepository.findByToken(VerificationToken);
    }

    @Override
    public VerificationToken generateNewVerificationToken(String existingVerificationToken) {
        VerificationToken vToken = verificationTokenRepository.findByToken(existingVerificationToken);
        vToken.updateToken(UUID.randomUUID().toString());
        vToken = verificationTokenRepository.save(vToken);
        return vToken;
    }

    @Override
    public User getUserByVerificationToken(String token) {
        return verificationTokenRepository.findByToken(token).getUser();
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        final PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);
    }

    @Override
    public PasswordResetToken getPasswordResetToken(String token) {
        return passwordTokenRepository.findByToken(token);
    }

    @Override
    public User getUserByPasswordResetToken(String token) {
        return passwordTokenRepository.findByToken(token).getUser();
    }

    @Override
    public void changeUserPassword(final User user, final String password) {
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        userRepository.save(user);
    }

    @Override
    public boolean checkIfValidOldPassword(final User user, final String oldPassword) {
        return new BCryptPasswordEncoder().matches(oldPassword, user.getPassword());
    }

//    NON API

    private boolean emailExist(String email) {
        return userRepository.findOneByEmail(email) != null;
    }
}
