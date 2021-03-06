package com.coursed.controller.rest;

import com.coursed.dto.AttestationGradeDTO;
import com.coursed.dto.AttestationsWrapper;
import com.coursed.service.AttestationGradeService;
import com.coursed.util.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hexray on 19.12.2016.
 */
@RestController
@RequestMapping("api/attestations")
public class AttestationResource {
    @Autowired
    private AttestationGradeService attestationGradeService;

    @GetMapping
    private ResponseEntity<GenericResponse> get(@RequestParam(value = "page") Integer page,
                                                @RequestParam(value = "size") Integer size) {
        return new ResponseEntity<>(new GenericResponse(attestationGradeService.getAll(page, size)), HttpStatus.OK);
    }

    @GetMapping("{attestationGradeId}")
    private ResponseEntity<GenericResponse> get(@PathVariable("attestationGradeId") Long attestationGradeId) {
        return new ResponseEntity<>(new GenericResponse(attestationGradeService.getById(attestationGradeId)), HttpStatus.OK);
    }

    // OLD
    @PostMapping("/createManyFirst")
    private void createSet(@RequestBody AttestationsWrapper attestationGrades) {
        List<AttestationGradeDTO> list = new ArrayList<>(attestationGrades);
        attestationGradeService.createManyFirst(list);
    }
    //Transferring to rest
    @PostMapping("/")
    private void createAttestations(@RequestBody AttestationsWrapper attestationGrades){
        List<AttestationGradeDTO> list = new ArrayList<>(attestationGrades);
        attestationGradeService.createManyFirst(list);
    }

    //For first post
    //@RequestMapping(value = "/{studentId}/disciplines/{disciplineId}/attestations/{attestationId}", method = RequestMethod.PUT)
//    @RequestMapping(value = "/{attestationId}", method = RequestMethod.PUT)
//    private ResponseEntity<GenericResponse> editAttestation(@PathVariable("attestationId") Long attestationId,
//                                                            @RequestBody AttestationGradeDTO attestationGrade)
//    {
//
//    }
}
