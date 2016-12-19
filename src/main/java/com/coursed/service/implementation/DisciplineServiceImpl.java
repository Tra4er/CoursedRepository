package com.coursed.service.implementation;

import com.coursed.dto.DisciplineDTO;
import com.coursed.model.*;
import com.coursed.model.enums.DisciplineType;
import com.coursed.model.enums.PlannedEventType;
import com.coursed.repository.*;
import com.coursed.service.DisciplineService;
import com.coursed.service.YearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private EducationPlanRepository educationPlanRepository;

    @Autowired
    private YearService yearService;

    @Autowired
    private PlannedEventRepository plannedEventRepository;

    @Override
    public List<Discipline> findAll() {
        return disciplineRepository.findAll();
    }

    @Override
    public Discipline create(DisciplineDTO disciplineDTO) {
        Discipline discipline = new Discipline();

        discipline.setName(disciplineDTO.getName());
        discipline.setType(disciplineDTO.getType());
        discipline.setHours(disciplineDTO.getHours());
        discipline.setCredits(disciplineDTO.getCredits());
        discipline.setCourseNumber(disciplineDTO.getCourseNumber());
        discipline.setSemesterNumber(disciplineDTO.getSemesterNumber());

        EducationPlan educationPlan = educationPlanRepository.findOne(disciplineDTO.getEducationPlanId());

        if (educationPlan == null)
            throw new IllegalArgumentException("educationPlan is null");

        discipline.setEducationPlan(educationPlan);

        return disciplineRepository.save(discipline);
    }

    @Override
    public void connectWithTeacher(Long disciplineId, Long teacherId) {
        Teacher teacher = teacherRepository.findOne(teacherId);
        Discipline discipline = disciplineRepository.findOne(disciplineId);

        List<Teacher> teachersList = discipline.getTeachers();
        teachersList.add(teacher);

        discipline.setTeachers(teachersList);
        disciplineRepository.save(discipline);
    }

    @Override
    public List<Discipline> getAllActualConnectedWithTeacher(Long teacherId, Long plannedEventId) {
        List<Discipline> disciplineList = disciplineRepository.findAll();
        Teacher teacher = teacherRepository.findOne(teacherId);

        Year currentYear = yearService.getCurrent();
        List<EducationPlan> educationPlans = currentYear.getEducationPlans();

        //// TODO: 18.12.2016  transfer to jpa to improve performance
        List<Discipline> connectedWithTeacher = disciplineList.stream()
                .filter(discipline -> discipline.getTeachers().contains(teacher))
                .collect(Collectors.toList());

        List<Discipline> actualDisciplines = new ArrayList<>();

        if (plannedEventId != null) {
            PlannedEvent plannedEvent = plannedEventRepository.findOne(plannedEventId);
            Semester semester = plannedEvent.getSemester();

            for (EducationPlan plan : educationPlans) {
                List<Discipline> disciplinesFromPlan = plan.getDisciplines();
                for (Discipline discipline : connectedWithTeacher) {

                    if (disciplinesFromPlan.contains(discipline) &&
                            discipline.getSemesterNumber() == semester.getSemesterNumber()) {

                        if((plannedEvent.getEventType() == PlannedEventType.GRADING_WEEK && discipline.getType() == DisciplineType.CREDIT) ||
                                (plannedEvent.getEventType() == PlannedEventType.GRADING_WEEK && discipline.getType() == DisciplineType.DIFFERENTIATED_CREDIT) ||
                                (plannedEvent.getEventType() == PlannedEventType.GRADING_WEEK && discipline.getType() == DisciplineType.COURSE_PROJECT) ||
                                (plannedEvent.getEventType() == PlannedEventType.EXAMINATION && discipline.getType() == DisciplineType.EXAM))
                            actualDisciplines.add(discipline);
                    }
                }
            }

        } else {
            for (EducationPlan plan : educationPlans) {
                List<Discipline> disciplinesFromPlan = plan.getDisciplines();
                for (Discipline discipline : connectedWithTeacher) {
                    if (disciplinesFromPlan.contains(discipline))
                        actualDisciplines.add(discipline);
                }
            }
        }

        return actualDisciplines;
    }
}
