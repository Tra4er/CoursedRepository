package com.coursed.service;

import com.coursed.dto.DisciplineDTO;
import com.coursed.model.Discipline;

import java.util.List;

/**
 * Created by Hexray on 11.12.2016.
 */
public interface DisciplineService {
    List<Discipline> findAll();
    Discipline create(DisciplineDTO disciplineDTO);
    void connectWithTeacher(Long disciplineId, Long teacherId);
    List<Discipline> getAllActualConnectedWithTeacher(Long teacherId, Long plannedEventId);
    List<Discipline> getAllDisciplinesFromPlannedEvent(Long plannedEventId);
}
