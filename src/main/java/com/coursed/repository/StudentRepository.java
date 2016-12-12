package com.coursed.repository;

import com.coursed.model.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Hexray on 19.11.2016.
 */
@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {
    List<Student> findAll();
}
