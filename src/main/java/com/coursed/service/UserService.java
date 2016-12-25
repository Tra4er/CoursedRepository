package com.coursed.service;

import com.coursed.dto.*;
import com.coursed.model.auth.PasswordResetToken;
import com.coursed.model.auth.Role;
import com.coursed.model.auth.User;
import com.coursed.model.auth.VerificationToken;

import java.util.List;
import java.util.Optional;

/**
 * Created by Hexray on 06.11.2016.
 */
public interface UserService {
    User registerStudent(UserStudentDTO registrationForm);
    User registerTeacher(UserTeacherDTO registrationForm);
    void saveRegisteredUser(User user);
    User getUserById(Long id);
    Optional<User> getUserByEmail(String email);
    List<User> findAllUnconfirmedTeachers();
    List<User> findAllTeachers(Long groupId);
    List<User> findAllGroupCurators(Long groupId);
    void deleteUser(Long userId);
    void connectUserWithRole(Long userId, Long roleId);
    void connectUserWithRole(User user, Role role);
    void makeATeacher(Long userId);
    void createVerificationTokenForUser(User user, String token);
    VerificationToken getVerificationToken(String VerificationToken);
    VerificationToken generateNewVerificationToken(String token);
    void createPasswordResetTokenForUser(User user, String token);
    PasswordResetToken getPasswordResetToken(String token);
    User getUserByPasswordResetToken(String token);
    void changeUserPassword(User user, String password);
    boolean checkIfValidOldPassword(User user, String password);
}
