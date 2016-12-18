package com.coursed.service.implementation;

import com.coursed.model.PlannedEvent;
import com.coursed.model.Semester;
import com.coursed.model.Year;
import com.coursed.model.enums.PlannedEventType;
import com.coursed.repository.PlannedEventRepository;
import com.coursed.service.PlannedEventService;
import com.coursed.service.YearService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Override
    public PlannedEvent findOne(String eventId) {
        try{
            return plannedEventRepository.findOne(Long.parseLong(eventId));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Wrong event id.");
        }
    }
    /**
     *  Returns list of events that have begin date from as in param
     * @param beginDateString date - string of format "1986-04-08T12:30:00"
     * @param expirationDateString date - string of format "1986-04-08T12:30:00"
     * @return list of events.
     */
    @Override
    public void create(String beginDateString, String expirationDateString, PlannedEventType plannedEventType, Semester semester) throws DateTimeParseException {
        LocalDateTime beginDate;
        LocalDateTime expirationDate;
        try {
            beginDate = LocalDateTime.parse(beginDateString);
            expirationDate = LocalDateTime.parse(expirationDateString);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Wrong date.");
        }

        if(semester == null) { // TODO mb i should go to semester rep and check
            throw new IllegalArgumentException("Wrong semester");
        }

        PlannedEvent plannedEvent = new PlannedEvent(beginDate, expirationDate, plannedEventType, semester);
        plannedEventRepository.save(plannedEvent);
    }

    @Override
    public void create(PlannedEvent event) {
        plannedEventRepository.save(event);
    }

    @Override
    public List<PlannedEvent> findAll() {
        LOGGER.debug("Getting all planned events");
        return plannedEventRepository.findAll();
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
        Year year = yearService.getCurrent();
        List<PlannedEvent> plannedEvents = new ArrayList<>();

        List<PlannedEvent> eventsFromFirstSemester = year.getSemesters().get(0).getPlannedEvents();
        List<PlannedEvent> eventsFromSecondSemester = year.getSemesters().get(1).getPlannedEvents();

        eventsFromFirstSemester.stream().forEach(plannedEvent -> plannedEvents.add(plannedEvent));
        eventsFromSecondSemester.stream().forEach(plannedEvent -> plannedEvents.add(plannedEvent));

        return null;
    }
}
