package com.coursed.repository;

import com.coursed.dto.TeacherDTO;
import com.coursed.model.Teacher;
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
            "from Teacher t")
    List<TeacherDTO> findAllInDTO();
}
