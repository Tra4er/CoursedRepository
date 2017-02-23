package com.coursed.dto;

import com.coursed.model.enums.SemesterNumber;

/**
 * Created by Trach on 2/23/2017.
 */
public class SemesterDTO {

    private Long id;
    private SemesterNumber semesterNumber;

    public SemesterDTO(Long id, SemesterNumber semesterNumber) {
        this.id = id;
        this.semesterNumber = semesterNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SemesterNumber getSemesterNumber() {
        return semesterNumber;
    }

    public void setSemesterNumber(SemesterNumber semesterNumber) {
        this.semesterNumber = semesterNumber;
    }
}
