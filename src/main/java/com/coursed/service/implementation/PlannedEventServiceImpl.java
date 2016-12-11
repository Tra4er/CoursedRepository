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

    // TODO implement methods
    @Override
    public void create(PlannedEvent event) {
        plannedEventRepository.save(event);
    }

    @Override
    public List<PlannedEvent> findAll() {
        LOGGER.debug("Getting all planned events");
        return plannedEventRepository.findAll();
    }

    @Override
    public List<PlannedEvent> findAllByBeginDate(Date date) {
        return null;
    }

    @Override
    public List<PlannedEvent> findAllByExpirationDate(Date date) {
        return null;
    }

    @Override
    public List<PlannedEvent> findAllByEventType(PlannedEventType plannedEventType) {
        return null;
    }

    @Override
    public List<PlannedEvent> findAllBySemester(Semester semester) {
        return null;
    }
}
