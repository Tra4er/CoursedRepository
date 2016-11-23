package com.coursed.repository;

import com.coursed.model.Speciality;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Hexray on 19.11.2016.
 */
@Repository
public interface SpecialityRepository extends CrudRepository<Speciality, Long> {
}
