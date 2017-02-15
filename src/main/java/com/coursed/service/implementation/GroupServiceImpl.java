package com.coursed.service.implementation;

import com.coursed.dto.StudentDTO;
import com.coursed.dto.TeacherDTO;
import com.coursed.model.*;
import com.coursed.model.enums.CourseNumber;
import com.coursed.model.enums.SemesterNumber;
import com.coursed.repository.*;
import com.coursed.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Hexray on 26.11.2016.
 */
@Service
public class GroupServiceImpl implements GroupService {
//    TODO LOGGER

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupServiceImpl.class);

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SpecialityRepository specialityRepository;

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Autowired
    private EducationPlanRepository educationPlanRepository;

    @Autowired
    private PlannedEventRepository plannedEventRepository;

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
    public void addCurator(Long groupId, Long teacherId) {
        Teacher curator = teacherRepository.findOne(teacherId);
        Group group = groupRepository.findOne(groupId);

        group.addCurator(curator);
        groupRepository.save(group);
    }

    @Override
    public List<TeacherDTO.TeacherTitleDTO> getCurators(Long groupId) {
        return groupRepository.findCurators(groupId);
    }

    @Override
    public void addStudent(Long groupId, Long studentId) {
        Student student = studentRepository.findOne(studentId);
        Group group = groupRepository.findOne(groupId);

        group.addStudent(student);
        groupRepository.save(group);
    }

    @Override
    public Page<StudentDTO.StudentTitleDTO> getStudents(Long groupId, int page, int size) {
        return groupRepository.findAllStudents(groupId, new PageRequest(page, size));
    }

    @Override
    public List<Group> findAllWithoutCurator(Long semesterId) {
        return null;
    }

    @Override
    public List<Group> findAllForGrading(Long disciplineId, SemesterNumber semesterNumber, CourseNumber courseNumber) {
        Discipline discipline = disciplineRepository.findOne(disciplineId);

        EducationPlan educationPlan = discipline.getEducationPlan();
//                educationPlanRepository.getById(educationPlanId);
        Year year = educationPlan.getYear();

        Semester semester = year.getSemesters().stream()
                .filter(sem -> sem.getSemesterNumber() == semesterNumber)
                .findFirst().get();

        List<Group> groups = semester.getGroups().stream()
                .filter(group -> group.getCourseNumber() == courseNumber)
                .collect(Collectors.toList());

        return groups;
    }

    @Override
    public List<Group> findAllByPlannedEvent(Long plannedEventId) {
        PlannedEvent plannedEvent = plannedEventRepository.findOne(plannedEventId);
        Semester semester = plannedEvent.getSemester();
        return semester.getGroups();
    }

    @Override
    public Group findOne(Long groupId) {
        return groupRepository.findOne(groupId);
    }

}
