package com.coursed.service.implementation;

import com.coursed.dto.AttestationDTO;
import com.coursed.model.AttestationGrade;
import com.coursed.model.Discipline;
import com.coursed.model.Student;
import com.coursed.repository.AttestationGradeRepository;
import com.coursed.repository.DisciplineRepository;
import com.coursed.repository.StudentRepository;
import com.coursed.service.AttestationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hexray on 19.12.2016.
 */
public class AttestationServiceImpl implements AttestationService {

    @Autowired
    private AttestationGradeRepository attestationGradeRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Override
    public void createManyFirst(List<AttestationDTO> attestationDTOList) {
        List<AttestationGrade> attestationGrades = new ArrayList<>(attestationDTOList.size());

        Discipline discipline = disciplineRepository.findOne(attestationDTOList.get(0).getDisciplineId());

        for (AttestationDTO attestationDTO : attestationDTOList)
        {
            AttestationGrade at = new AttestationGrade();
            at.setFirstTry(Boolean.getBoolean(attestationDTO.getFirstTry()));
            at.setSecondTry(false);

            Student student = studentRepository.findOne(attestationDTO.getStudentId());
            at.setStudent(student);

            at.setDiscipline(discipline);

            attestationGrades.add(at);
        }

        attestationGradeRepository.save(attestationGrades);
    }
}
