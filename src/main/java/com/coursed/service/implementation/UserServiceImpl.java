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
