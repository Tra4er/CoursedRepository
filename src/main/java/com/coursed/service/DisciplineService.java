package com.coursed.service;

import com.coursed.dto.DisciplineForm;
import com.coursed.model.Discipline;

import java.util.List;

/**
 * Created by Hexray on 11.12.2016.
 */
public interface DisciplineService {
    List<Discipline> findAll();
    Discipline create(DisciplineForm disciplineForm);
    void connectWithTeacher(Long disciplineId, Long teacherId);
    List<Discipline> getAllActualConnectedWithTeacher(Long teacherId, Long plannedEventId);
}
