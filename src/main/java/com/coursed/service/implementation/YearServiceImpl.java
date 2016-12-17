package com.coursed.service.implementation;

import com.coursed.dto.YearForm;
import com.coursed.model.Semester;
import com.coursed.model.Year;
import com.coursed.model.enums.SemesterNumber;
import com.coursed.repository.SemesterRepository;
import com.coursed.repository.YearRepository;
import com.coursed.service.YearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.util.resources.cldr.aa.CalendarData_aa_DJ;

import java.util.*;

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
    public Year findOne(Long id) {
        return yearRepository.findOne(id);
    }

    @Override
    public void create(YearForm yearForm) {

        Year year = new Year();
        year.setBeginYear(yearForm.getBeginYear());
        year.setEndYear(yearForm.getEndYear());

        Semester firstSemester = new Semester(SemesterNumber.FIRST);
        Semester secondSemester = new Semester(SemesterNumber.SECOND);

        List<Semester> semesters = new ArrayList<>();
        semesters.add(firstSemester);
        semesters.add(secondSemester);
        year.setSemesters(semesters);

        firstSemester.setYear(year);
        secondSemester.setYear(year);

        yearRepository.save(year);
    }

    @Override
    public Year getCurrent() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH) + 1;

        Optional<Year> searchedYear;

        if(month >= 1 && month <= 8)
            searchedYear = yearRepository.findAll().stream()
                    .filter(year -> year.getEndYear() == cal.get(Calendar.YEAR))
                    .findFirst();
        else//(month >= 9 && month <= 12)
            searchedYear = yearRepository.findAll().stream()
                    .filter(year -> year.getBeginYear() == cal.get(Calendar.YEAR))
                    .findFirst();

        if(!searchedYear.isPresent())
            searchedYear = yearRepository.findAll().stream()
                    .max((y1, y2) -> Integer.compare(y1.getBeginYear(), y2.getBeginYear()));

        return searchedYear.get();
    }

}
