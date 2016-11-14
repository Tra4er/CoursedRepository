package com.coursed.model;

import com.coursed.model.enums.PlannedEventType;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Hexray on 14.11.2016.
 */
@Entity
public class PlannedEvent {
    @Id
    @GeneratedValue
    private Long id;
    private Date beginDate;
    private Date expirationDate;
    private Date creationDate;
    @Enumerated
    private PlannedEventType eventType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="semester_id")
    private Semester semester;

    public PlannedEvent() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
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
}
