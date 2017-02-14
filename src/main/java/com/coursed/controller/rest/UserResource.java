package com.coursed.controller.rest;

import com.coursed.model.auth.User;
import com.coursed.service.UserService;
import com.coursed.util.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by Trach on 11/24/2016.
 */
@RestController
@RequestMapping("/api/users")
public class UserResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

    @Autowired
    private UserService userService;

    //    @PreAuthorize("hasAnyRole('HEAD', 'SECRETARY')")
    @GetMapping
    public ResponseEntity<GenericResponse> get() {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                userService.getAll()), HttpStatus.OK);
    }

    //    @PreAuthorize("hasAnyRole('HEAD', 'SECRETARY')")
    @GetMapping("/search")
    public ResponseEntity<GenericResponse> search(@RequestParam(value = "filter", required = false) String filter,
                                                  @RequestParam(value = "curatorsOfGroup", required = false) Long groupId) {
        if(filter != null) {
            if (filter.equals("unconfirmed")) {
                return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                        userService.getAllUnconfirmedTeachers()), HttpStatus.OK);
            }
        }
        if (groupId != null) {
            return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                    userService.getAllGroupCurators(groupId)), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //    @PreAuthorize("hasAnyRole('HEAD', 'SECRETARY')")
    @GetMapping("{id}")
    public ResponseEntity<GenericResponse> getByUsername(@PathVariable("id") Long id) {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                userService.getById(id)), HttpStatus.OK);
    }

    //    @PreAuthorize("hasAnyRole('HEAD')")
    @PostMapping("/{id}/confirm-teacher")
    public ResponseEntity<GenericResponse> confirmTeacher(@PathVariable("id") Long id) {
        userService.makeATeacher(id);
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                userService.getById(id)), HttpStatus.OK);
    }

    //    @PreAuthorize("hasAnyRole('HEAD')")
    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse> delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                userService.getById(id)), HttpStatus.OK);
    }

    //TODO: solve n+1 JPA problem via avoiding traversal of unfetched entities when JSON is creating
//    @GetMapping("/getAllUnconfirmed")
//    private Set<Object> getAllUnconfirmed() {
//
//        Set<Object> json = new HashSet<>();
//
//        List<User> UnconfirmedTeachers = userService.getAllUnconfirmed();
//
//        for (User user : UnconfirmedTeachers) {
//            Map<String, Object> value = new HashMap<>();
//
//            value.put("id", user.getId());
//            value.put("email", user.getEmail());
//
//            json.add(value);
//        }
//
//        return json;
//    }

}
