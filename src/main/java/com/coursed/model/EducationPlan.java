package com.coursed.model;

import com.coursed.model.enums.CourseNumber;
import com.coursed.model.enums.GroupDegree;
import com.coursed.model.enums.GroupType;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Hexray on 11.12.2016.
 */
@Entity
public class EducationPlan {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated
    private GroupType groupType;
    @Enumerated
    private GroupDegree groupDegree;
    @Enumerated
    private CourseNumber courseNumber;

    @OneToMany(mappedBy = "educationPlan", fetch = FetchType.LAZY)
    private List<Discipline> disciplines;

    @JsonBackReference("speciality-educationplan")
    @ManyToOne
    @JoinColumn(name="speciality_id")
    private Speciality speciality;

    @JsonBackReference("year-educationplan")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="year_id")
    private Year year;

    public EducationPlan() {
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public List<Discipline> getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(List<Discipline> disciplines) {
        this.disciplines = disciplines;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public void setGroupType(GroupType groupType) {
        this.groupType = groupType;
    }

    public GroupDegree getGroupDegree() {
        return groupDegree;
    }

    public void setGroupDegree(GroupDegree groupDegree) {
        this.groupDegree = groupDegree;
    }

    public CourseNumber getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(CourseNumber courseNumber) {
        this.courseNumber = courseNumber;
    }
}
