package com.coursed.service.implementation;

import com.coursed.dto.SemesterDTO;
import com.coursed.model.Semester;
import com.coursed.repository.SemesterRepository;
import com.coursed.service.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Hexray on 04.12.2016.
 */
@Service
public class SemesterServiceImpl implements SemesterService {

    @Autowired
    private SemesterRepository semesterRepository;

    @Override
    public SemesterDTO getById(Long id) {
        return semesterRepository.findOneInDTO(id);
    }

    @Override
    public Page<SemesterDTO> getAll(int page, int size) {
       return semesterRepository.findAllInDTO(new PageRequest(page, size));
    }

    @Override
    public Page<SemesterDTO> getAllByYear(Long yearId, int page, int size) {
        return semesterRepository.findAllByYearInDTO(yearId, new PageRequest(page, size));
    }

}
