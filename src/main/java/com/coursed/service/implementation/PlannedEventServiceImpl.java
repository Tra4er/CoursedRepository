package com.coursed.service.implementation;

import com.coursed.model.PlannedEvent;
import com.coursed.model.Semester;
import com.coursed.model.enums.PlannedEventType;
import com.coursed.repository.PlannedEventRepository;
import com.coursed.service.PlannedEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
     * @param dateString date - string of format "1986-04-08 12:30"
     * @return list of events.
     */
    @Override
    public List<PlannedEvent> findAllByBeginDate(String dateString) {
        LocalDateTime date = LocalDateTime.parse(dateString);
        return plannedEventRepository.findAllByBeginDate(date);
    }

    /**
     *  Returns list of events that have expiration date as in param
     * @param dateString date - string of format "1986-04-08 12:30"
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
}
