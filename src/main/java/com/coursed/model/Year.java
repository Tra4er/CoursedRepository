package com.coursed.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;


/**
 * Created by Hexray on 13.11.2016.
 */
@Entity
public class Year {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private Integer beginYear;

    @JsonManagedReference("year-semester")
    @OneToMany(mappedBy = "year")
    private List<Semester> semesters;

    public Year() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBeginYear() {
        return beginYear;
    }

    public void setBeginYear(Integer beginYear) {
        this.beginYear = beginYear;
    }

    public List<Semester> getSemesters() {
        return semesters;
    }

    public void setSemesters(List<Semester> semesters) {
        this.semesters = semesters;
    }
}
