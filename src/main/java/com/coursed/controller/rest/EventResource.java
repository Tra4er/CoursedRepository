package com.coursed.controller.rest;

import com.coursed.model.PlannedEvent;
import com.coursed.service.PlannedEventService;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Created by Trach on 12/11/2016.
 */
@RestController
@RequestMapping("/api/events")
public class EventResource {

    @Autowired
    private PlannedEventService plannedEventService;

    @GetMapping("/getAll")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class) // TODO remove
    public Collection<PlannedEvent> getAll() {
        return plannedEventService.findAll();
    }

    @GetMapping("/getAllFromCurrentYear")
    public Collection<PlannedEvent> getAllFromCurrentYear(){
        return plannedEventService.findAllFromCurrentYear();
    }
}
