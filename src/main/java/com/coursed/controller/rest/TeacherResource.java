package com.coursed.controller.rest;

import com.coursed.dto.UserTeacherDTO;
import com.coursed.error.exception.UserAlreadyExistException;
import com.coursed.model.auth.User;
import com.coursed.registration.OnRegistrationCompleteEvent;
import com.coursed.service.DisciplineService;
import com.coursed.service.TeacherService;
import com.coursed.service.UserService;
import com.coursed.util.GenericResponse;
import com.coursed.validator.RecaptchaResponseDTOValidator;
import com.coursed.validator.UserTeacherDTOValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by Hexray on 17.12.2016.
 */
@RestController
@RequestMapping("/api/teachers")
public class TeacherResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherResource.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private DisciplineService disciplineService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private UserTeacherDTOValidator userTeacherDTOValidator;

    @Autowired
    private RecaptchaResponseDTOValidator recaptchaResponseDTOValidator;

    @InitBinder("userTeacherDTO")
    public void initTeacherBinder(WebDataBinder binder) {
        binder.addValidators(recaptchaResponseDTOValidator, userTeacherDTOValidator);
    }

    //    @PreAuthorize("hasAnyRole('HEAD', 'SECRETARY')")
    @GetMapping
    public ResponseEntity<GenericResponse> get(@RequestParam(value = "page") Integer page,
                                               @RequestParam(value = "size") Integer size) {

        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                teacherService.getAll(page, size)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GenericResponse> registerTeacherAccount(@Valid @RequestBody UserTeacherDTO userTeacherDTO,
                                                                  final HttpServletRequest request) {
        LOGGER.debug("Registering user account with information: {}", userTeacherDTO);

        User registered = userService.registerTeacher(userTeacherDTO);
        if (registered == null) {
            System.out.println("Exception");
            throw new UserAlreadyExistException();
        }

        final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), appUrl));

        return new ResponseEntity<>(new GenericResponse(HttpStatus.CREATED.value(), "success", registered.getId()),
                HttpStatus.CREATED);
    }

    //    @PreAuthorize("hasAnyRole('HEAD', 'SECRETARY')")
    @GetMapping("/search")
    public ResponseEntity<GenericResponse> search(@RequestParam(value = "page", required = false) Integer page,
                                                  @RequestParam(value = "size", required = false) Integer size,
                                                  @RequestParam(value = "filter", required = false) String filter,
                                                  @RequestParam(value = "disciplineId", required = false) Long disciplineId) {
        if (filter != null) {
            if (filter.equals("unconfirmed")) {
                return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                        teacherService.getAllUnconfirmed(page, size)), HttpStatus.OK);
            }
            if(disciplineId != null) {
                if (filter.equals("withDiscipline")) {
                    return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                            teacherService.getAllTeachersWithDiscipline(disciplineId)), HttpStatus.OK);
                }
                if (filter.equals("withoutDiscipline")) {
                    return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                            teacherService.getAllTeachersWithoutDiscipline(disciplineId)), HttpStatus.OK);
                }
            }
        }

        return new ResponseEntity<>(new GenericResponse(HttpStatus.NO_CONTENT.value(), "success"), HttpStatus.NO_CONTENT);
    }

    //    @PreAuthorize("hasAnyRole('HEAD', 'SECRETARY')")
    @GetMapping("{id}")
    public ResponseEntity<GenericResponse> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                teacherService.getById(id)), HttpStatus.OK);
    }

    //    @PreAuthorize("hasAnyRole('HEAD', 'SECRETARY')")
    @DeleteMapping("{id}")
    public ResponseEntity<GenericResponse> deleteById(@PathVariable("id") Long id) {
        teacherService.delete(id);
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success"), HttpStatus.OK);
    }

    //    @PreAuthorize("hasAnyRole('HEAD', 'SECRETARY')")
    @PostMapping("{id}/confirm")
    public ResponseEntity<GenericResponse> confirm(@PathVariable("id") Long id) {
        teacherService.confirmTeacher(id);
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                teacherService.getById(id)), HttpStatus.OK);
    }

    @GetMapping("{teacherId}/disciplines")
    public ResponseEntity<GenericResponse> getDisciplines(@PathVariable("teacherId") Long teacherId,
                                                          @RequestParam(value = "page", required = false) Integer page,
                                                          @RequestParam(value = "size", required = false) Integer size) {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                disciplineService.getAllByTeacher(teacherId, page, size)), HttpStatus.OK);
    }

}
