package com.coursed.repository;

import com.coursed.dto.UserDTO;
import com.coursed.model.auth.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Hexray on 16.10.2016.
 * Edited by Trach on 07.11.2016
 */

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findOneByEmail(String email);
    List<User> findAll();


//    @Query("SELECT users FROM User users INNER JOIN users.roles " +
//            "WHERE role_id = SELECT role.id FROM Role role WHERE role.name = 'ROLE_TEACHER'")
    //AND role_id IN SELECT r.id FROM Role r WHERE r.name = 'ROLE_TEACHER'
//    @Query("SELECT DISTINCT u FROM User AS u LEFT JOIN FETCH u.roles WHERE u.isATeacher = true AND role_id != 3")
//    List<User> findAllUnconfirmedTeachers();

    @Query("SELECT t.user FROM PasswordResetToken t WHERE t.token = ?1")
    User findOneByPasswordResetTokenInDTO(String token);

    @Query("SELECT new com.coursed.dto.UserDTO(u.id, u.email) FROM User u WHERE u.studentEntity.id = ?1)")
    UserDTO findOneByStudentInDTO(Long studentId);

    @Query("SELECT new com.coursed.dto.UserDTO(u.id, u.email) FROM User u WHERE u.teacherEntity.id = ?1)")
    UserDTO findOneByTeacherInDTO(Long teacherId);

    @Query("SELECT u FROM User u WHERE u.isATeacher = true AND u.id NOT IN (" +
            "SELECT u2.id FROM User u2 JOIN u2.roles r where r.name = 'ROLE_TEACHER')")
    List<User> findAllUnconfirmedTeachers();

//    @Query("SELECT dis FROM Discipline dis INNER JOIN dis.teachers WHERE teacher_id = :teacherid")

//    Role role = roleRepository.findByName("ROLE_TEACHER");
//
//    //TODO transfer to JPQL
//        return userRepository.getAllCuratorsOfGroup().stream()
//                .filter(User::isATeacher)
//                .filter(user -> !(user.getRoles().contains(role)))
//            .collect(Collectors.toList());
}

