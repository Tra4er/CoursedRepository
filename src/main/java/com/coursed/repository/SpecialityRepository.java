package com.coursed.repository;

import com.coursed.dto.SpecialityDTO;
import com.coursed.dto.StudentDTO;
import com.coursed.model.Speciality;
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
public interface SpecialityRepository extends CrudRepository<Speciality, Long> {
    List<Speciality> findAll();

    @Query("SELECT new com.coursed.dto.SpecialityDTO(s.id, s.fullName, s.groupsName) " +
            "FROM Speciality s WHERE s.id = ?1")
    SpecialityDTO findOneInDTO(Long specialityId);

    @Query("SELECT new com.coursed.dto.SpecialityDTO(s.id, s.fullName, s.groupsName) " +
            "FROM Speciality s")
    Page<SpecialityDTO> findAllInDTO(Pageable pageable);
}
