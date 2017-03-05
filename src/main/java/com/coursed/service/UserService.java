package com.coursed.service;

import com.coursed.dto.*;
import com.coursed.model.auth.PasswordResetToken;
import com.coursed.model.auth.Role;
import com.coursed.model.auth.User;
import com.coursed.model.auth.VerificationToken;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;

/**
 * Created by Hexray on 06.11.2016.
 */
public interface UserService {
    void saveRegisteredUser(User user);
    User getById(Long id);
    User getByEmail(String email);
    UserDTO getByStudentId(Long studentId);
    UserDTO getByTeacherId(Long teacherId);
    List<User> getAll();
    boolean checkIfUserExists(String email);
    List<User> getAllUnconfirmedTeachers();
    List<User> getAllTeachers(Long groupId);
    List<User> getAllGroupCurators(Long groupId);
    void deleteUser(Long userId);
    void connectUserWithRole(Long userId, Long roleId);
    void connectUserWithRole(User user, Role role);
    void makeATeacher(Long userId);
    void createVerificationTokenForUser(UserDTO userDTO, String token);
    VerificationToken getVerificationToken(String VerificationToken);
    VerificationToken generateNewVerificationToken(String token);
    User getUserByVerificationToken(String token);
    void createPasswordResetTokenForUser(User user, String token);
    PasswordResetToken getPasswordResetToken(String token);
    User getUserByPasswordResetToken(String token);
    void changeUserPassword(User user, String password);
    boolean checkIfValidOldPassword(User user, String password);
}
