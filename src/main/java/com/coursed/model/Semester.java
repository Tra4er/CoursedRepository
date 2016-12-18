package com.coursed.model;


import com.coursed.model.enums.SemesterNumber;
import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Hexray on 13.11.2016.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Semester.class)
@Entity
public class Semester {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated
    private SemesterNumber semesterNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="year_id")
    private Year year;

    @OneToMany(mappedBy = "semester", fetch = FetchType.LAZY)
    private List<Group> groups;

    @OneToMany(mappedBy = "semester", fetch = FetchType.LAZY)
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
