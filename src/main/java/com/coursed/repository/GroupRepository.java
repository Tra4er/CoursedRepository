package com.coursed.repository;

import com.coursed.dto.GroupDTO;
import com.coursed.dto.StudentDTO;
import com.coursed.dto.TeacherDTO;
import com.coursed.model.Group;
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
public interface GroupRepository extends CrudRepository<Group, Long> {
    List<Group> findAll();

    @Query("SELECT new com.coursed.dto.GroupDTO(g.id, g.number, g.groupType, g.groupDegree, g.courseNumber, g.semester.id, " +
            "g.speciality.id) FROM com.coursed.model.Group g WHERE g.id = ?1")
    GroupDTO findOneInDTO(Long groupId);

    @Query("SELECT new com.coursed.dto.GroupDTO(g.id, g.number, g.groupType, g.groupDegree, g.courseNumber, g.semester.id, " +
            "g.speciality.id) FROM com.coursed.model.Group g")
    Page<GroupDTO> findAllInDTO(Pageable pageable);

    @Query("SELECT new com.coursed.dto.GroupDTO(g.id, g.number, g.groupType, g.groupDegree, g.courseNumber, g.semester.id, " +
            "g.speciality.id) FROM com.coursed.model.Group g WHERE g.curators IS EMPTY")
    Page<GroupDTO> findAllWithoutCuratorsInDTO(Pageable pageable);

    @Query("SELECT new com.coursed.dto.TeacherDTO$TeacherTitleDTO(c.id, c.firstName, c.lastName, c.patronymic) " +
            "FROM com.coursed.model.Group g LEFT JOIN g.curators c WHERE g.id = ?1")
    List<TeacherDTO.TeacherTitleDTO> findCuratorsInDTO(Long groupId);

    @Query("SELECT new com.coursed.dto.StudentDTO$StudentTitleDTO(s.id, s.firstName, s.lastName, s.patronymic) " +
            "FROM com.coursed.model.Group g LEFT JOIN g.students s WHERE g.id = ?1")
    Page<StudentDTO.StudentTitleDTO> findAllStudentsInDTO(Long groupId, Pageable pageable);
}
