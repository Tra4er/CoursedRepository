package com.coursed.controller.rest;

import com.coursed.model.auth.User;
import com.coursed.service.UserService;
import com.coursed.util.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @GetMapping("{username}")
    public ResponseEntity<GenericResponse> getByUsername(@PathVariable("username") String username) {
        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), "success",
                userService.getUserByEmail(username)), HttpStatus.OK);
    }

    // OLD

    @PostMapping("/confirm-teacher")
    public void confirmTeacher(@RequestParam(name = "userId") Long userId) {
        userService.makeATeacher(userId);
    }

    //TODO: solve n+1 JPA problem via avoiding traversal of unfetched entities when JSON is creating
//    @GetMapping("/getAllUnconfirmedTeachers")
//    private Set<Object> getAllUnconfirmedTeachers() {
//
//        Set<Object> json = new HashSet<>();
//
//        List<User> UnconfirmedTeachers = userService.findAllUnconfirmedTeachers();
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

    @GetMapping("/getAllUnconfirmedTeachers")
    private Collection<User> getAllUnconfirmedTeachers() {
        return userService.findAllUnconfirmedTeachers();
    }

    @GetMapping("/getAllTeachers") // TODO create separate controller
    private Collection<User> getAllTeachers(@RequestParam(name = "groupId", required = false) Long groupId) {
        return userService.findAllTeachers(groupId);
    }

    @GetMapping("/getAllGroupCurators")
    private Collection<User> getAllGroupCurators(@RequestParam(name = "groupId") Long groupId) {
        return userService.findAllGroupCurators(groupId);
    }

    @GetMapping("/deleteUser") // TODO delete method
    private void deleteUser(@RequestParam(name = "userId") Long userId) {
        userService.deleteUser(userId);
    }

}
