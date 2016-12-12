package com.coursed.service;

import com.coursed.model.Semester;

import java.util.List;

/**
 * Created by Hexray on 04.12.2016.
 */
public interface SemesterService {
    Semester findOne(Long id);
    List<Semester> findAll();
}
