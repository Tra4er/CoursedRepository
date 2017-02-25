package com.coursed.repository;

import com.coursed.dto.YearDTO;
import com.coursed.model.Year;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Hexray on 13.11.2016.
 */
public interface YearRepository extends CrudRepository<Year, Long> {
    List<Year> findAll();

    @Query("SELECT new com.coursed.dto.YearDTO(y.id, y.beginYear, y.endYear) " +
            "FROM Year y WHERE y.id = ?1")
    YearDTO findOneInDTO(Long yearId);

    @Query("SELECT new com.coursed.dto.YearDTO(y.id, y.beginYear, y.endYear) " +
            "FROM Year y WHERE y.beginYear = ?1")
    YearDTO findOneByYearInDTO(Integer year);

    @Query("SELECT new com.coursed.dto.YearDTO(y.id, y.beginYear, y.endYear) " +
            "FROM Year y ")
    Page<YearDTO> findAllInDTO(Pageable pageable);
}
