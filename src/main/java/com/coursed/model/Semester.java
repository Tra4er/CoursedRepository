package com.coursed.model;


import com.coursed.model.enums.SemesterNumber;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Hexray on 13.11.2016.
 */
@Entity
public class Semester {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated
    private SemesterNumber semesterNumber;

//    @JsonBackReference("year-semesters")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="year_id")
    private Year year;

//    @JsonIgnore
    //@JsonManagedReference("semester-groups")
    @OneToMany(mappedBy = "semester")
    private List<Group> groups;

//    @JsonIgnore
    @OneToMany(mappedBy = "semester")
    private List<PlannedEvent> plannedEvents;

    public Semester() {
    }

    public Semester(SemesterNumber semesterNumber) {
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

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<PlannedEvent> getPlannedEvents() {
        return plannedEvents;
    }

    public void setPlannedEvents(List<PlannedEvent> plannedEvents) {
        this.plannedEvents = plannedEvents;
    }
}
