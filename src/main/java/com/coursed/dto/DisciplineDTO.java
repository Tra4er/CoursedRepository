package com.coursed.dto;

import com.coursed.model.enums.CourseNumber;
import com.coursed.model.enums.DisciplineType;
import com.coursed.model.enums.SemesterNumber;

/**
 * Created by Hexray on 11.12.2016.
 */
public class DisciplineDTO {

    public static class DisciplineTitleDTO {

        private Long id;
        private String name;
        private DisciplineType type;
        private CourseNumber courseNumber;

        public DisciplineTitleDTO(Long id, String name, DisciplineType type, CourseNumber courseNumber) {
            this.id = id;
            this.name = name;
            this.type = type;
            this.courseNumber = courseNumber;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

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

        public CourseNumber getCourseNumber() {
            return courseNumber;
        }

        public void setCourseNumber(CourseNumber courseNumber) {
            this.courseNumber = courseNumber;
        }
    }

    private Long id;
    private String name;
    private DisciplineType type;
    private Float hours;
    private Float credits;
    private CourseNumber courseNumber;
    private SemesterNumber semesterNumber;
    private Long educationPlanId;

    public DisciplineDTO(Long id, String name, DisciplineType type, Float hours, Float credits, CourseNumber courseNumber,
                         SemesterNumber semesterNumber, Long educationPlanId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.hours = hours;
        this.credits = credits;
        this.courseNumber = courseNumber;
        this.semesterNumber = semesterNumber;
        this.educationPlanId = educationPlanId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
