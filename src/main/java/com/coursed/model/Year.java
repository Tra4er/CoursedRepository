package com.coursed.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @Column(nullable = false, unique = true)
    private Integer endYear;

    @JsonManagedReference("year-semesters")
    @OneToMany(mappedBy = "year", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Semester> semesters;

    //    @JsonIgnore
    @JsonManagedReference("year-educationplan")
    @OneToMany(mappedBy = "year", /*fetch = FetchType.LAZY,*/ cascade = CascadeType.ALL)
    private List<EducationPlan> educationPlans;

    public Year() {
    }

    public Year(Integer beginYear, Integer endYear) {
        this.beginYear = beginYear;
        this.endYear = endYear;
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

    public Integer getEndYear() {
        return endYear;
    }

    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }

    public List<Semester> getSemesters() {
        return semesters;
    }

    public void setSemesters(List<Semester> semesters) {
        this.semesters = semesters;
    }
}
