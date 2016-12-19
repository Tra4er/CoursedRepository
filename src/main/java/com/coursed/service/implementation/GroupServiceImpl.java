package com.coursed.service.implementation;

import com.coursed.model.*;
import com.coursed.model.enums.CourseNumber;
import com.coursed.model.enums.SemesterNumber;
import com.coursed.repository.DisciplineRepository;
import com.coursed.repository.EducationPlanRepository;
import com.coursed.repository.GroupRepository;
import com.coursed.repository.SemesterRepository;
import com.coursed.repository.SpecialityRepository;
import com.coursed.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Hexray on 26.11.2016.
 */
@Service
public class GroupServiceImpl implements GroupService {
//    TODO LOGGER

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private SpecialityRepository specialityRepository;

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Autowired
    private EducationPlanRepository educationPlanRepository;

    @Override
    public Group create(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    @Override
    public List<Group> findAllFromSpeciality(Long specialityId) {
        Speciality speciality = specialityRepository.findOne(specialityId);
        if(speciality != null)
        {
            return speciality.getGroups();
        }

        return null; // TODO throw new IllegalArgumentException()
    }

    @Override
    public List<Group> findAllFromSemester(Long semesterId) {
        Semester semester = semesterRepository.findOne(semesterId);
        if(semester != null)
        {
            return semester.getGroups();
        }

        return null;
    }

    @Override
    public List<Group> findAllFromSpecialityAndSemester(Long specialityId, Long semesterId) {
        Speciality speciality = specialityRepository.findOne(specialityId);
        Semester semester = semesterRepository.findOne(semesterId);
        //Intersection
        if(semester != null && speciality != null)
        {
            return semester.getGroups().stream().filter(speciality.getGroups()::contains).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<Group> findAllWithoutCurator(Long semesterId) {
        return null;
    }

    @Override
    public List<Group> findAllForGrading(Long disciplineId, SemesterNumber semesterNumber, CourseNumber courseNumber) {
        Discipline discipline = disciplineRepository.findOne(disciplineId);

        EducationPlan educationPlan = discipline.getEducationPlan();
//                educationPlanRepository.findOne(educationPlanId);
        Year year = educationPlan.getYear();

        Semester semester = year.getSemesters().stream()
                .filter(sem -> sem.getSemesterNumber() == semesterNumber)
                .findFirst().get();

        List<Group> groups = semester.getGroups().stream()
                .filter(group -> group.getCourseNumber() == courseNumber)
                .collect(Collectors.toList());

        return groups;
    }
}
