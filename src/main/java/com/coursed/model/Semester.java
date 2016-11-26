package com.coursed.model;


import com.coursed.model.enums.SemesterNumber;
import com.fasterxml.jackson.annotation.JsonBackReference;

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

    @JsonBackReference("year-semester")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="year_id")
    private Year year;

    @OneToMany(mappedBy = "semester")
    private List<EdGroup> edGroups;

    @OneToMany(mappedBy = "semester")
    private List<PlannedEvent> plannedEvents;

    public Semester() {
    }

    public Semester(SemesterNumber semesterNumber, Year year) {
        this.semesterNumber = semesterNumber;
        this.year = year;
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

    public List<EdGroup> getEdGroups() {
        return edGroups;
    }

    public void setEdGroups(List<EdGroup> edGroups) {
        this.edGroups = edGroups;
    }

    public List<PlannedEvent> getPlannedEvents() {
        return plannedEvents;
    }

    public void setPlannedEvents(List<PlannedEvent> plannedEvents) {
        this.plannedEvents = plannedEvents;
    }
}
