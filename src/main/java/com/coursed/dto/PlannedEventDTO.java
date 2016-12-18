package com.coursed.dto;

import com.coursed.model.enums.PlannedEventType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * Created by Hexray on 18.12.2016.
 */
public class PlannedEventDTO {
    private ZonedDateTime beginDate;
    private ZonedDateTime expirationDate;
    private PlannedEventType eventType;
    private Long semesterId;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public ZonedDateTime getBeginDate() {
        return beginDate;
    }

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    public void setBeginDate(ZonedDateTime beginDate) {
        this.beginDate = beginDate;
    }

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public ZonedDateTime getExpirationDate() {
        return expirationDate;
    }

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    public void setExpirationDate(ZonedDateTime expirationDate) {
        this.expirationDate = expirationDate;
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
