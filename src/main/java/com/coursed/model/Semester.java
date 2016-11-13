package com.coursed.model;


import javax.persistence.*;

/**
 * Created by Hexray on 13.11.2016.
 */
@Entity
public class Semester {
    @Id
    @GeneratedValue
    private Long id;

    private String semesterNumber;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="YEAR_ID")
    private Year year;

    public Semester() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSemesterNumber() {
        return semesterNumber;
    }

    public void setSemesterNumber(String semesterNumber) {
        this.semesterNumber = semesterNumber;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }
}
