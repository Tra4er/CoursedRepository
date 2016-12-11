package com.coursed.repository;

import com.coursed.model.EducationPlan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Hexray on 11.12.2016.
 */
public interface EducationPlanRepository extends CrudRepository<EducationPlan, Long> {
    List<EducationPlan> findAll();
}
