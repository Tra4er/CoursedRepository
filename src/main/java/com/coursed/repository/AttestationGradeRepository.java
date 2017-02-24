package com.coursed.repository;

import com.coursed.dto.AttestationGradeDTO;
import com.coursed.model.AttestationGrade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Hexray on 19.11.2016.
 */
@Repository
public interface AttestationGradeRepository extends CrudRepository<AttestationGrade, Long> {


    @Query("SELECT new com.coursed.dto.AttestationGradeDTO(a.id, a.firstTry, a.secondTry, a.student.id, a.discipline.id) " +
            "FROM AttestationGrade a WHERE a.id = ?1")
    AttestationGradeDTO findOneInDTO(Long attestationGradeId);

    @Query("SELECT new com.coursed.dto.AttestationGradeDTO(a.id, a.firstTry, a.secondTry, a.student.id, a.discipline.id) " +
            "FROM AttestationGrade a")
    Page<AttestationGradeDTO> findAllInDTO(Pageable pageable);
}
