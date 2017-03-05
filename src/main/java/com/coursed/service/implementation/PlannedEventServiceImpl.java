package com.coursed.service.implementation;

import com.coursed.dto.PlannedEventDTO;
import com.coursed.model.PlannedEvent;
import com.coursed.model.Semester;
import com.coursed.model.Year;
import com.coursed.model.enums.PlannedEventType;
import com.coursed.repository.PlannedEventRepository;
import com.coursed.repository.SemesterRepository;
import com.coursed.service.PlannedEventService;
import com.coursed.service.YearService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Trach on 12/11/2016.
 */
@Service
public class PlannedEventServiceImpl implements PlannedEventService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private PlannedEventRepository plannedEventRepository;

    @Autowired
    private YearService yearService;

    @Autowired
    private SemesterRepository semesterRepository;

    @Override
    public PlannedEventDTO getById(Long eventId) {
        return plannedEventRepository.findOneInDTO(eventId);
    }
    /**

     */
    @Override
    public PlannedEventDTO create(PlannedEventDTO plannedEventDTO){
        PlannedEvent plannedEvent = new PlannedEvent();

        plannedEvent.setBeginDate(plannedEventDTO.getBeginDate());
        plannedEvent.setExpirationDate(plannedEventDTO.getExpirationDate());
        plannedEvent.setCreationDate(LocalDateTime.now());

        plannedEvent.setEventType(plannedEventDTO.getEventType());

        Semester semester = semesterRepository.findOne(plannedEventDTO.getSemesterId());
        plannedEvent.setSemester(semester);

        PlannedEvent saved = plannedEventRepository.save(plannedEvent);

        return plannedEventRepository.findOneInDTO(saved.getId());
    }

    @Override
    public Page<PlannedEventDTO> getAll(int page, int size) {
        LOGGER.debug("Getting all planned events");
        return plannedEventRepository.findAllInDTO(new PageRequest(page, size));
    }

    @Override
    public Page<PlannedEventDTO> getAllUpcoming(int page, int size) {
        LOGGER.debug("Getting all upcoming planned events");
//        Date now = Date.from(Instant.now());
        LocalDateTime now = LocalDateTime.now();
        return plannedEventRepository.findAllUpcomingInDTO(now,
                new PageRequest(page, size, Sort.Direction.ASC, "beginDate"));
    }

    @Override
    public Page<PlannedEventDTO> getAllPast(int page, int size) {
        LOGGER.debug("Getting all past planned events");
//        Date now = Date.from(Instant.now());
        LocalDateTime now = LocalDateTime.now();
        return plannedEventRepository.findAllPastInDTO(now,
                new PageRequest(page, size, Sort.Direction.ASC, "expirationDate"));
    }

    @Override
    public Page<PlannedEventDTO> getAllInProgress(int page, int size) {
        LOGGER.debug("Getting all in progress planned events");
//        Date now = Date.from(Instant.now());
        LocalDateTime now = LocalDateTime.now();
        return plannedEventRepository.findAllInProgressInDTO(now,
                new PageRequest(page, size, Sort.Direction.ASC, "beginDate"));
    }

    @Override
    public Page<PlannedEventDTO> getAllByCurrentYear(int page, int size) {
        LocalDate now = LocalDate.now();
        LocalDateTime startYear = LocalDateTime.of(now.getYear(), 1, 1, 0, 0);
        LocalDateTime endYear = LocalDateTime.of(now.getYear(), 12, 31, 23, 59);
        return plannedEventRepository.findAllByYearInDTO(startYear, endYear, new PageRequest(page, size));
    }

    /**
     *  Returns list of events that have begin date from as in param
     * @param dateString date - string of format "1986-04-08T12:30:00"
     * @return list of events.
     */
    @Override
    public List<PlannedEvent> findAllByBeginDate(String dateString) {
        LocalDateTime date = LocalDateTime.parse(dateString);
        return plannedEventRepository.findAllByBeginDate(date);
    }

//    Instant instant = Instant.parse(creationDate.toString());
//        instant.toEpochMilli(); TODO
    /**
     *  Returns list of events that have expiration date as in param
     * @param dateString date - string of format "1986-04-08T12:30:00"
     * @return list of events.
     */
    @Override
    public List<PlannedEvent> findAllByExpirationDate(String dateString) {
        LocalDateTime date = LocalDateTime.parse(dateString);
        return plannedEventRepository.findAllByExpirationDate(date);
    }

    @Override
    public List<PlannedEvent> findAllByEventType(PlannedEventType plannedEventType) {
        return plannedEventRepository.findAllByEventType(plannedEventType);
    }

    @Override
    public List<PlannedEvent> findAllBySemester(Semester semester) {
        return plannedEventRepository.findAllBySemester(semester);
    }

    @Override
    public List<PlannedEvent> findAllFromCurrentYear() {
        return null; // TODO
    }
}
