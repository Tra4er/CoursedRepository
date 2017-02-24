package com.coursed.service;

import com.coursed.dto.PlannedEventDTO;
import com.coursed.model.PlannedEvent;
import com.coursed.model.Semester;
import com.coursed.model.enums.PlannedEventType;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by Trach on 12/11/2016.
 */
public interface PlannedEventService {
    PlannedEventDTO getById(Long eventId);
    PlannedEvent create(PlannedEventDTO plannedEventDTO) throws DateTimeParseException;
    PlannedEvent create(PlannedEvent event);
    Page<PlannedEventDTO> getAll(int page, int size);
    Page<PlannedEventDTO> getAllUpcoming(int page, int size);
    Page<PlannedEventDTO> getAllPast(int page, int size);
    Page<PlannedEventDTO> getAllInProgress(int page, int size);
    List<PlannedEvent> findAllByBeginDate(String date);
    List<PlannedEvent> findAllByExpirationDate(String date);
    List<PlannedEvent> findAllByEventType(PlannedEventType plannedEventType);
    List<PlannedEvent> findAllBySemester(Semester semester);
    List<PlannedEvent> findAllFromCurrentYear();
}
