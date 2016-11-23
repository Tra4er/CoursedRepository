package com.coursed.repository;

import com.coursed.model.Year;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

/**
 * Created by Hexray on 13.11.2016.
 */
public interface YearRepository extends CrudRepository<Year, Long> {
    List<Year> findAll();
}
