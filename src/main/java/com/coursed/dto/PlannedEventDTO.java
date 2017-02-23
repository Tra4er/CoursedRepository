package com.coursed.dto;

import com.coursed.model.enums.PlannedEventType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * Created by Hexray on 18.12.2016.
 */
public class PlannedEventDTO {

    private Long id;
    private LocalDateTime beginDate;
    private LocalDateTime expirationDate;
    private LocalDateTime creationDate;
    private PlannedEventType eventType;
    private Long semesterId;

    public PlannedEventDTO(Long id, LocalDateTime beginDate, LocalDateTime expirationDate, LocalDateTime creationDate,
                           PlannedEventType eventType, Long semesterId) {
        this.id = id;
        this.beginDate = beginDate;
        this.expirationDate = expirationDate;
        this.creationDate = creationDate;
        this.eventType = eventType;
        this.semesterId = semesterId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDateTime beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public PlannedEventType getEventType() {
        return eventType;
    }

    public void setEventType(PlannedEventType eventType) {
        this.eventType = eventType;
    }

    public Long getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(Long semesterId) {
        this.semesterId = semesterId;
    }
}
