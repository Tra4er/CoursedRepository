package com.coursed.repository;

import com.coursed.dto.TeacherDTO;
import com.coursed.model.Group;
import com.coursed.model.Teacher;
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

    @Query("SELECT new com.coursed.dto.TeacherDTO(c.id, c.firstName, c.lastName, c.patronymic, c.phoneNumber) " +
            "FROM com.coursed.model.Group g LEFT JOIN g.curators c WHERE g.id = ?1")
    List<TeacherDTO> findCurators(Long groupId);
}
