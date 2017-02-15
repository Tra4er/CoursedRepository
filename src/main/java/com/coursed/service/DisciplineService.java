package com.coursed.service;

import com.coursed.dto.DisciplineDTO;
import com.coursed.model.Discipline;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Hexray on 11.12.2016.
 */
public interface DisciplineService {
    Discipline getById(Long id);
    List<Discipline> getAll();
    Discipline create(DisciplineDTO disciplineDTO);
    void connectWithTeacher(Long disciplineId, Long teacherId);
    Page<DisciplineDTO> getAllByTeacher(Long teacherId, int page, int size);
    List<Discipline> getAllActualConnectedWithTeacher(Long teacherId, Long plannedEventId);
    List<Discipline> getAllDisciplinesFromPlannedEvent(Long plannedEventId, Long groupId);
}
