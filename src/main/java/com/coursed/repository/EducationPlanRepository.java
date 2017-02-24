package com.coursed.repository;

import com.coursed.dto.EducationPlanDTO;
import com.coursed.model.EducationPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Hexray on 11.12.2016.
 */
public interface EducationPlanRepository extends CrudRepository<EducationPlan, Long> {
    List<EducationPlan> findAll();

    @Query("SELECT new com.coursed.dto.EducationPlanDTO(e.id, e.year.id, e.speciality.id, e.groupType, e.groupDegree, " +
            "e.courseNumber) FROM EducationPlan e WHERE e.id = ?1")
    EducationPlanDTO findOneInDTO(Long educationPlanId);

    @Query("SELECT new com.coursed.dto.EducationPlanDTO(e.id, e.year.id, e.speciality.id, e.groupType, e.groupDegree, " +
            "e.courseNumber) FROM EducationPlan e")
    Page<EducationPlanDTO> findAllInDTO(Pageable pageable);
}
