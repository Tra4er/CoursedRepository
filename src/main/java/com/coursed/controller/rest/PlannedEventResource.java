package com.coursed.controller.rest;

import com.coursed.dto.PlannedEventDTO;
import com.coursed.model.PlannedEvent;
import com.coursed.service.PlannedEventService;
import com.coursed.util.GenericResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Collection;

/**
 * Created by Trach on 12/11/2016.
 */
@RestController
@RequestMapping("/api/events")
public class PlannedEventResource {

    @Autowired
    private PlannedEventService plannedEventService;

    @GetMapping
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    public ResponseEntity<GenericResponse> get() {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                plannedEventService.findAll()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GenericResponse> post(@RequestBody PlannedEventDTO plannedEventDTO) {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.CREATED.value(), "success",
                plannedEventService.create(plannedEventDTO)), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<GenericResponse> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                plannedEventService.findOne(id)), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<GenericResponse> search(@RequestParam("filter") String filter) {
        if (filter.equals("currentYear")) {
            return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                    plannedEventService.findAllFromCurrentYear()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new GenericResponse(HttpStatus.NO_CONTENT.value(), "success"), HttpStatus.NO_CONTENT);
    }

}
