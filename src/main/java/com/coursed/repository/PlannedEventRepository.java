package com.coursed.repository;

import com.coursed.model.PlannedEvent;
import com.coursed.model.Semester;
import com.coursed.model.enums.PlannedEventType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Hexray on 19.11.2016.
 */
@Repository
public interface PlannedEventRepository extends CrudRepository<PlannedEvent, Long> {
    List<PlannedEvent> findAll();
    List<PlannedEvent> findAllByBeginDate(LocalDateTime date);
    List<PlannedEvent> findAllByExpirationDate(LocalDateTime date);
    List<PlannedEvent> findAllByEventType(PlannedEventType plannedEventType);
    List<PlannedEvent> findAllBySemester(Semester semester);
}
