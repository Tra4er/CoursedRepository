package com.coursed.service;

import com.coursed.dto.*;
import com.coursed.model.auth.Role;
import com.coursed.model.auth.User;
import com.coursed.model.auth.VerificationToken;

import java.util.List;
import java.util.Optional;

/**
 * Created by Hexray on 06.11.2016.
 * Changed by Trach on 07.11.2016
 */
public interface UserService {
    User registerStudent(UserStudentRegistrationForm registrationForm);
    User registerTeacher(UserTeacherRegistrationForm registrationForm);
    void saveRegisteredUser(final User user);
    User getUserById(Long id);
    Optional<User> getUserByEmail(String email);
    List<User> findAll();
    List<User> findAllUnconfirmedTeachers();
    List<User> findAllTeachers(Long groupId);
    void connectUserWithRole(Long userId, Long roleId);
    void connectUserWithRole(User user, Role role);
    void makeATeacher(Long userId);
    void createVerificationTokenForUser(final User user, final String token);
    VerificationToken getVerificationToken(final String VerificationToken);
}
