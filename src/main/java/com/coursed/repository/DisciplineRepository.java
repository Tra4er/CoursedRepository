package com.coursed.repository;

import com.coursed.model.Discipline;
import com.coursed.model.Semester;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Hexray on 19.11.2016.
 */
@Repository
public interface DisciplineRepository extends CrudRepository<Discipline, Long> {
    List<Discipline> findAll();
}
