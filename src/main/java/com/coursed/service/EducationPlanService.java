package com.coursed.service;

import com.coursed.dto.EducationPlanDTO;
import com.coursed.model.EducationPlan;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Hexray on 11.12.2016.
 */
public interface EducationPlanService {
    EducationPlanDTO getById(Long educationPlanId);
    EducationPlanDTO create(EducationPlanDTO planForm);
    Page<EducationPlanDTO> getAll(int page, int size);
    Page<EducationPlanDTO> getAllByYear(int page, int size, Long yearId);
}
