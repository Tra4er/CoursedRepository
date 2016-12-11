package com.coursed.service;

import com.coursed.model.PlannedEvent;
import com.coursed.model.Semester;
import com.coursed.model.enums.PlannedEventType;

import java.util.Date;
import java.util.List;

/**
 * Created by Trach on 12/11/2016.
 */
public interface PlannedEventService {
    void create(PlannedEvent event);
    List<PlannedEvent> findAll();
    List<PlannedEvent> findAllByBeginDate(Date date);
    List<PlannedEvent> findAllByExpirationDate(Date date);
    List<PlannedEvent> findAllByEventType(PlannedEventType plannedEventType);
    List<PlannedEvent> findAllBySemester(Semester semester);
}
