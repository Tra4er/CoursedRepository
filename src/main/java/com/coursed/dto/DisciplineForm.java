package com.coursed.dto;

import com.coursed.model.enums.CourseNumber;
import com.coursed.model.enums.DisciplineType;
import com.coursed.model.enums.SemesterNumber;

/**
 * Created by Hexray on 11.12.2016.
 */
public class DisciplineForm {
    private String name;
    private DisciplineType type;
    private Float hours;
    private Float credits;
    private CourseNumber courseNumber;
    private SemesterNumber semesterNumber;
    private Long educationPlanId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DisciplineType getType() {
        return type;
    }

    public void setType(DisciplineType type) {
        this.type = type;
    }

    public Float getHours() {
        return hours;
    }

    public void setHours(Float hours) {
        this.hours = hours;
    }

    public Float getCredits() {
        return credits;
    }

    public void setCredits(Float credits) {
        this.credits = credits;
    }

    public CourseNumber getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(CourseNumber courseNumber) {
        this.courseNumber = courseNumber;
    }

    public SemesterNumber getSemesterNumber() {
        return semesterNumber;
    }

    public void setSemesterNumber(SemesterNumber semesterNumber) {
        this.semesterNumber = semesterNumber;
    }

    public Long getEducationPlanId() {
        return educationPlanId;
    }

    public void setEducationPlanId(Long educationPlanId) {
        this.educationPlanId = educationPlanId;
    }
}
