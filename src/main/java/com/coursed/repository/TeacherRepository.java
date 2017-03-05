package com.coursed.repository;

import com.coursed.dto.TeacherDTO;
import com.coursed.model.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Hexray on 19.11.2016.
 */
@Repository
public interface TeacherRepository extends CrudRepository<Teacher, Long> {

    List<Teacher> findAll();

    @Query("SELECT new com.coursed.dto.TeacherDTO(t.id, t.firstName, t.lastName, t.patronymic, t.phoneNumber) " +
            "FROM Teacher t WHERE t.id = ?1")
    TeacherDTO findOneInDTO(Long id);

    @Query("SELECT new com.coursed.dto.TeacherDTO$TeacherTitleDTO(t.id, t.firstName, t.lastName, t.patronymic) " +
            "FROM Teacher t")
    Page<TeacherDTO.TeacherTitleDTO> findAllInDTO(Pageable pageable);

    @Query("SELECT new com.coursed.dto.TeacherDTO$TeacherTitleDTO(t.id, t.firstName, t.lastName, t.patronymic) " +
            "FROM Teacher t WHERE t.user.id " +
            "NOT IN (SELECT u.id FROM User u JOIN u.roles r where r.name = 'ROLE_TEACHER')")
    Page<TeacherDTO.TeacherTitleDTO> findAllUnconfirmedInDTO(Pageable pageable);

    @Query("SELECT new com.coursed.dto.TeacherDTO$TeacherTitleDTO(t.id, t.firstName, t.lastName, t.patronymic) " +
            "FROM Teacher t JOIN t.disciplines d WHERE ?1 IN (d.id)")
    Page<TeacherDTO.TeacherTitleDTO> findAllByDisciplineInDTO(Long disciplineId, Pageable pageable);

    @Query("SELECT new com.coursed.dto.TeacherDTO$TeacherTitleDTO(t.id, t.firstName, t.lastName, t.patronymic) " +
            "FROM Teacher t JOIN t.disciplines d WHERE ?1 NOT IN (d.id)")
    Page<TeacherDTO.TeacherTitleDTO> findAllWithoutDisciplineInDTO(Long disciplineId, Pageable pageable);

    @Query("SELECT new com.coursed.dto.TeacherDTO$TeacherTitleDTO(t.id, t.firstName, t.lastName, t.patronymic) " +
            "FROM Teacher t JOIN t.curatedGroups g WHERE ?1 IN (g.id)")
    Page<TeacherDTO.TeacherTitleDTO> findAllCuratorsByGroupInDTO(Long groupId, Pageable pageable);

    @Query("SELECT new com.coursed.dto.TeacherDTO$TeacherTitleDTO(t.id, t.firstName, t.lastName, t.patronymic) " +
            "FROM Teacher t JOIN t.curatedGroups g WHERE ?1 NOT IN (g.id)")
    Page<TeacherDTO.TeacherTitleDTO> findAllNotCuratorsByGroupInDTO(Long groupId, Pageable pageable);
}
