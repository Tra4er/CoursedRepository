package com.coursed.service;

import com.coursed.model.Semester;
import com.coursed.model.Year;
import com.coursed.model.enums.SemesterNumber;
import com.coursed.repository.SemesterRepository;
import com.coursed.repository.YearRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Hexray on 16.11.2016.
 */
@Service
public class YearServiceImpl implements YearService {

    @Autowired
    private YearRepository yearRepository;

    @Autowired
    private SemesterRepository semesterRepository;

    @Override
    public List<Year> findAll() {
        return yearRepository.findAll();
    }

    @Override
    public void create(Year year) {

        Semester semester1 = new Semester();
        semester1.setSemesterNumber(SemesterNumber.FIRST);

        Semester semester2 = new Semester();
        semester2.setSemesterNumber(SemesterNumber.SECOND);

        List<Semester> semesters = new ArrayList<>();

        semesters.add(semester1);
        semesters.add(semester2);

        year.setSemesters(semesters);

        yearRepository.save(year);
    }

    @Override
    public void update(Year year) {

    }
}
