package com.coursed.repository;

import com.coursed.model.Semester;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Hexray on 13.11.2016.
 */
public interface SemesterRepository extends CrudRepository<Semester, Long> {
    //List<Semester> findAll();
}
