package com.coursed.repository;

import com.coursed.dto.SemesterDTO;
import com.coursed.model.Semester;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Hexray on 13.11.2016.
 */
public interface SemesterRepository extends CrudRepository<Semester, Long> {
    List<Semester> findAll();

    @Query("SELECT new com.coursed.dto.SemesterDTO(s.id, s.semesterNumber) " +
            "FROM Semester s WHERE s.id = ?1")
    SemesterDTO findOneInDTO(Long semesterId);

    @Query("SELECT new com.coursed.dto.SemesterDTO(s.id, s.semesterNumber) " +
            "FROM Semester s")
    Page<SemesterDTO> findAllInDTO(Pageable pageable);
}
