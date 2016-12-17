package com.coursed.model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


/**
 * Created by Hexray on 13.11.2016.
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
@Entity
public class Year {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private Integer beginYear;

    @Column(nullable = false, unique = true)
    private Integer endYear;

    @OneToMany(mappedBy = "year", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Semester> semesters;

    @OneToMany(mappedBy = "year", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EducationPlan> educationPlans;

    public Year() {
    }

    public List<EducationPlan> getEducationPlans() {
        return educationPlans;
    }

    public void setEducationPlans(List<EducationPlan> educationPlans) {
        this.educationPlans = educationPlans;
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
