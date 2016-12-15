package com.coursed.service.implementation;

import com.coursed.dto.EducationPlanForm;
import com.coursed.model.EducationPlan;
import com.coursed.model.Speciality;
import com.coursed.model.Year;
import com.coursed.model.enums.CourseNumber;
import com.coursed.model.enums.GroupDegree;
import com.coursed.model.enums.GroupType;
import com.coursed.repository.EducationPlanRepository;
import com.coursed.repository.SpecialityRepository;
import com.coursed.repository.YearRepository;
import com.coursed.service.EducationPlanService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<EducationPlan> findAll() {
        return educationPlanRepository.findAll();
    }

    @Override
    public EducationPlan create(EducationPlanForm planForm) {
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
    public EducationPlan findOne(Long educationPlanId) {
        return educationPlanRepository.findOne(educationPlanId);
    }
}
