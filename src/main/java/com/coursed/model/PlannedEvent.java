package com.coursed.model;

import com.coursed.model.enums.PlannedEventType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.*;

/**
 * Created by Hexray on 14.11.2016.
 */
@Entity
public class PlannedEvent {
    @Id
    @GeneratedValue
    private Long id;
    private ZonedDateTime beginDate;
    private ZonedDateTime expirationDate;
    private ZonedDateTime creationDate;
    @Enumerated
    private PlannedEventType eventType;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="semester_id")
    private Semester semester;

    public PlannedEvent() {
    }

    /**
     *  Returns list of events that have expiration date as in param
     * @param beginDate date - string of format "1986-04-08T12:30"
     * @param expirationDate date - string of format "1986-04-08T12:30"
     * @param eventType enum of {@link com.coursed.model.enums.PlannedEventType} type
     * @param semester object of {@link com.coursed.model.Semester} class
     * @return list of events.
     */
    public PlannedEvent(String beginDate, String expirationDate, PlannedEventType eventType, Semester semester) {
        this.beginDate = ZonedDateTime.parse(beginDate);
        this.expirationDate = ZonedDateTime.parse(expirationDate);
        this.creationDate = ZonedDateTime.now();
        this.eventType = eventType;
        this.semester = semester;
    }

    public PlannedEvent(ZonedDateTime beginDate, ZonedDateTime expirationDate, PlannedEventType eventType, Semester semester) {
        this.beginDate = beginDate;
        this.expirationDate = expirationDate;
        this.creationDate = ZonedDateTime.now();
        this.eventType = eventType;
        this.semester = semester;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(ZonedDateTime beginDate) {
        this.beginDate = beginDate;
    }

    public ZonedDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(ZonedDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public PlannedEventType getEventType() {
        return eventType;
    }

    public void setEventType(PlannedEventType eventType) {
        this.eventType = eventType;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    @Override
    public String toString() {
        return "PlannedEvent{" +
                "id=" + id +
                ", beginDate=" + beginDate +
                ", expirationDate=" + expirationDate +
                ", creationDate=" + creationDate +
                ", eventType=" + eventType +
                ", semester=" + semester.getSemesterNumber() +
                '}';
    }
}
