package com.coursed.controller.rest;

import com.coursed.dto.PlannedEventDTO;
import com.coursed.service.GroupService;
import com.coursed.service.PlannedEventService;
import com.coursed.util.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Trach on 12/11/2016.
 */
@RestController
@RequestMapping("/api/events")
public class PlannedEventResource {

    @Autowired
    private PlannedEventService plannedEventService;

    @Autowired
    private GroupService groupService;

    @GetMapping
    public ResponseEntity<GenericResponse> get(@RequestParam(value = "page") Integer page,
                                               @RequestParam(value = "size") Integer size) {
        return new ResponseEntity<>(new GenericResponse(
                plannedEventService.getAll(page, size)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GenericResponse> post(@RequestBody PlannedEventDTO plannedEventDTO) {
        return new ResponseEntity<>(new GenericResponse(plannedEventService.create(plannedEventDTO)), HttpStatus.CREATED);
    }

    @GetMapping("{plannedEventId}")
    public ResponseEntity<GenericResponse> getById(@PathVariable("plannedEventId") Long plannedEventId) {
        return new ResponseEntity<>(new GenericResponse(plannedEventService.getById(plannedEventId)), HttpStatus.OK);
    }

    @GetMapping("{plannedEventId}/groups")
    public ResponseEntity<GenericResponse> getGroups(@PathVariable("plannedEventId") Long plannedEventId,
                                                     @RequestParam(value = "page") Integer page,
                                                     @RequestParam(value = "size") Integer size) {
        return new ResponseEntity<>(new GenericResponse(
                groupService.getAllByPlannedEvent(plannedEventId, page, size)), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<GenericResponse> search(@RequestParam(value = "filter", required = false) String filter,
                                                  @RequestParam(value = "page") Integer page,
                                                  @RequestParam(value = "size") Integer size) {
        if(filter != null) {
            switch(filter) {
                case "upcoming" : {
                    return new ResponseEntity<>(new GenericResponse(
                            plannedEventService.getAllUpcoming(page, size)), HttpStatus.OK);
                }
                case "past" : {
                    return new ResponseEntity<>(new GenericResponse(
                            plannedEventService.getAllPast(page, size)), HttpStatus.OK);
                }
                case "inProgress" : {
                    return new ResponseEntity<>(new GenericResponse(
                            plannedEventService.getAllInProgress(page, size)), HttpStatus.OK);
                }
                case "currentYear" : {
                    return new ResponseEntity<>(new GenericResponse(
                            plannedEventService.getAllByCurrentYear(page, size)), HttpStatus.OK);
                }
                default:break;
            }
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

