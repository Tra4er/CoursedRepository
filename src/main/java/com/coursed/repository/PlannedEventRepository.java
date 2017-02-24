package com.coursed.repository;

import com.coursed.dto.PlannedEventDTO;
import com.coursed.model.PlannedEvent;
import com.coursed.model.Semester;
import com.coursed.model.enums.PlannedEventType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
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

    @Query("SELECT new com.coursed.dto.PlannedEventDTO(e.id, e.beginDate, e.expirationDate, e.creationDate, " +
            "e.eventType, e.semester.id) FROM PlannedEvent e WHERE e.id = ?1")
    PlannedEventDTO findOneInDTO(Long plannedEventId);

    @Query("SELECT new com.coursed.dto.PlannedEventDTO(e.id, e.beginDate, e.expirationDate, e.creationDate, " +
            "e.eventType, e.semester.id) FROM PlannedEvent e")
    Page<PlannedEventDTO> findAllInDTO(Pageable pageable);

    @Query("SELECT new com.coursed.dto.PlannedEventDTO(e.id, e.beginDate, e.expirationDate, e.creationDate, " +
            "e.eventType, e.semester.id) FROM PlannedEvent e WHERE e.beginDate >= ?1")
    Page<PlannedEventDTO> findAllUpcomingInDTO(LocalDateTime now, Pageable pageable);

    @Query("SELECT new com.coursed.dto.PlannedEventDTO(e.id, e.beginDate, e.expirationDate, e.creationDate, " +
            "e.eventType, e.semester.id) FROM PlannedEvent e WHERE e.expirationDate <= ?1")
    Page<PlannedEventDTO> findAllPastInDTO(LocalDateTime now, Pageable pageable);

    @Query("SELECT new com.coursed.dto.PlannedEventDTO(e.id, e.beginDate, e.expirationDate, e.creationDate, " +
            "e.eventType, e.semester.id) FROM PlannedEvent e WHERE ?1 BETWEEN e.beginDate AND e.expirationDate ")
    Page<PlannedEventDTO> findAllInProgressInDTO(LocalDateTime now, Pageable pageable);
}
