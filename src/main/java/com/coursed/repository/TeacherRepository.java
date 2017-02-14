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

    List<Teacher> findAll(); // TODO temporary. Remove this.
    List<Teacher> findAll(Pageable pageable);

    @Query("select new com.coursed.dto.TeacherDTO(t.id, t.firstName, t.lastName, t.patronymic, t.phoneNumber) " +
            "from Teacher t WHERE t.id = ?1")
    TeacherDTO findOneInDTO(Long id);

    @Query("select new com.coursed.dto.TeacherDTO$TeacherTitleDTO(t.id, t.firstName, t.lastName, t.patronymic) " +
            "from Teacher t")
    Page<TeacherDTO.TeacherTitleDTO> findAllInDTO(Pageable pageable);

    @Query("SELECT new com.coursed.dto.TeacherDTO$TeacherTitleDTO(t.id, t.firstName, t.lastName, t.patronymic) " +
            "FROM Teacher t WHERE t.user.id " +
            "NOT IN (SELECT u.id FROM User u JOIN u.roles r where r.name = 'ROLE_TEACHER')")
    Page<TeacherDTO.TeacherTitleDTO> findAllUnconfirmedInDTO(Pageable pageable);
}
