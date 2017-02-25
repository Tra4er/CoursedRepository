package com.coursed.service.implementation;

import com.coursed.dto.YearDTO;
import com.coursed.model.Semester;
import com.coursed.model.Year;
import com.coursed.model.enums.SemesterNumber;
import com.coursed.repository.SemesterRepository;
import com.coursed.repository.YearRepository;
import com.coursed.service.YearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
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
    public Page<YearDTO> getAll(int page, int size) {
        return yearRepository.findAllInDTO(new PageRequest(page, size, Sort.Direction.DESC, "beginYear"));
    }

    @Override
    public YearDTO getById(Long yearId) {
        return yearRepository.findOneInDTO(yearId);
    }

    @Override
    public Year create(YearDTO yearDTO) {

        Year year = new Year();
        year.setBeginYear(yearDTO.getBeginYear());
        year.setEndYear(yearDTO.getEndYear());

        Semester firstSemester = new Semester(SemesterNumber.FIRST);
        Semester secondSemester = new Semester(SemesterNumber.SECOND);

        List<Semester> semesters = new ArrayList<>();
        semesters.add(firstSemester);
        semesters.add(secondSemester);
        year.setSemesters(semesters);

        firstSemester.setYear(year);
        secondSemester.setYear(year);

        return yearRepository.save(year);
    }

    @Override
    public YearDTO getCurrent() { // TODO
//        Date now = Date.from(Instant.now());
//        return yearRepository.findOneByDateInDTO(now);
        return null;
    }

}
