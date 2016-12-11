package com.coursed.service.implementation;

import com.coursed.dto.DisciplineForm;
import com.coursed.model.Discipline;
import com.coursed.model.EducationPlan;
import com.coursed.model.Speciality;
import com.coursed.repository.DisciplineRepository;
import com.coursed.repository.EducationPlanRepository;
import com.coursed.repository.SpecialityRepository;
import com.coursed.repository.TeacherRepository;
import com.coursed.service.DisciplineService;
import com.sun.javaws.exceptions.InvalidArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Hexray on 11.12.2016.
 */
@Service
public class DisciplineServiceImpl implements DisciplineService {
    @Autowired
    private DisciplineRepository disciplineRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SpecialityRepository specialityRepository;

    @Autowired
    private EducationPlanRepository educationPlanRepository;

    @Override
    public List<Discipline> findAll() {
        return disciplineRepository.findAll();
    }

    @Override
    public Discipline create(DisciplineForm disciplineForm) {
        Discipline discipline = new Discipline();
        discipline.setCourseNumber(disciplineForm.getCourseNumber());

        Speciality speciality = specialityRepository.findOne(disciplineForm.getSpecialityId());
        EducationPlan educationPlan = educationPlanRepository.findOne(disciplineForm.getEducationPlanId());

        if(speciality == null || educationPlan == null)
            throw new NullPointerException("speciality of educationPlan is null");

        discipline.setSpeciality(speciality);
        discipline.setCredits(disciplineForm.getCredits());

        discipline.setName(disciplineForm.getName());



        return null;
    }

    @Override
    public void connectWithTeacher(Long disciplineId, Long teacherId) {

    }
}
