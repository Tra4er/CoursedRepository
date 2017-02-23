package com.coursed.service.implementation;

import com.coursed.dto.EducationPlanDTO;
import com.coursed.model.EducationPlan;
import com.coursed.model.Speciality;
import com.coursed.model.Year;
import com.coursed.repository.EducationPlanRepository;
import com.coursed.repository.SpecialityRepository;
import com.coursed.repository.YearRepository;
import com.coursed.service.EducationPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Hexray on 11.12.2016.
 */
@Service
public class EducationPlanServiceImpl implements EducationPlanService {
    @Autowired
    private EducationPlanRepository educationPlanRepository;

    @Autowired
    private YearRepository yearRepository;

    @Autowired
    private SpecialityRepository specialityRepository;

    @Override
    public EducationPlanDTO getById(Long educationPlanId) {
        return educationPlanRepository.findOneInDTO(educationPlanId);
    }

    @Override
    public EducationPlan create(EducationPlanDTO planForm) {
        EducationPlan educationPlan = new EducationPlan();
        educationPlan.setCourseNumber(planForm.getCourseNumber());
        educationPlan.setGroupDegree(planForm.getGroupDegree());
        educationPlan.setGroupType(planForm.getGroupType());

        Speciality speciality = specialityRepository.findOne(planForm.getSpecialityId());
        Year year = yearRepository.findOne(planForm.getYearId());

        if(speciality == null || year == null)
            throw new IllegalArgumentException("Speciality or year is null");

        educationPlan.setSpeciality(speciality);
        educationPlan.setYear(year);

        return educationPlanRepository.save(educationPlan);
    }

    @Override
    public Page<EducationPlanDTO> getAll(int page, int size) {
        return educationPlanRepository.findAllInDTO(new PageRequest(page, size));
    }

}
