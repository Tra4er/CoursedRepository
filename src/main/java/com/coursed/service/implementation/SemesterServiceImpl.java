package com.coursed.service.implementation;

import com.coursed.model.Semester;
import com.coursed.repository.SemesterRepository;
import com.coursed.service.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Semester findOne(Long id) {
        return semesterRepository.findOne(id);
    }

    @Override
    public List<Semester> findAll() {
        return semesterRepository.findAll();
    }
}
