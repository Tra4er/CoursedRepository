package com.coursed.service.implementation;

import com.coursed.dto.AttestationGradeDTO;
import com.coursed.model.AttestationGrade;
import com.coursed.model.Discipline;
import com.coursed.model.Student;
import com.coursed.repository.AttestationGradeRepository;
import com.coursed.repository.DisciplineRepository;
import com.coursed.repository.StudentRepository;
import com.coursed.service.AttestationGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hexray on 19.12.2016.
 */
@Service
public class AttestationGradeServiceImpl implements AttestationGradeService {

    @Autowired
    private AttestationGradeRepository attestationGradeRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Override
    public AttestationGradeDTO getById(Long attestationGradeId) {
        return attestationGradeRepository.findOneInDTO(attestationGradeId);
    }

    @Override
    public Page<AttestationGradeDTO> getAll(int page, int size) {
        return attestationGradeRepository.findAllInDTO(new PageRequest(page, size));
    }

    @Override
    public void createManyFirst(List<AttestationGradeDTO> attestationGradeDTOList) {
        List<AttestationGrade> attestationGrades = new ArrayList<>(attestationGradeDTOList.size());

        Discipline discipline = disciplineRepository.findOne(attestationGradeDTOList.get(0).getDisciplineId());

        for (AttestationGradeDTO attestationGradeDTO : attestationGradeDTOList)
        {
            AttestationGrade at = new AttestationGrade();
            if("false".compareTo(attestationGradeDTO.getFirstTry()) == 0)
                at.setFirstTry(false);
            else at.setFirstTry(true);

            at.setSecondTry(false);

            Student student = studentRepository.findOne(attestationGradeDTO.getStudentId());
            at.setStudent(student);

            at.setDiscipline(discipline);

            attestationGrades.add(at);
        }

        attestationGradeRepository.save(attestationGrades);
    }
}
