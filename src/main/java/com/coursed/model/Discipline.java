package com.coursed.model;

import com.coursed.model.enums.CourseNumber;
import com.coursed.model.enums.DisciplineType;
import com.coursed.model.enums.SemesterNumber;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Hexray on 14.11.2016.
 */
@Entity
public class Discipline {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Enumerated
    private DisciplineType type;
    private Float hours;
    private Float credits;
    @Enumerated
    private CourseNumber courseNumber;
    @Enumerated
    private SemesterNumber semesterNumber;

    @ManyToMany
    @JoinTable(name = "discipline_teachers", joinColumns = @JoinColumn(name = "discipline_id"), inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    private List<Teacher> teachers;

    @OneToMany(mappedBy = "discipline")
    private List<FinalGrade> finalGrades;

    @OneToMany(mappedBy = "discipline")
    private List<AttestationGrade> attestationGrades;

    @ManyToOne
    @JoinColumn(name="speciality_id")
    private Speciality speciality;

    public Discipline() {
    }

    public Discipline(String name, DisciplineType type, Float hours, Float credits, CourseNumber courseNumber, SemesterNumber semesterNumber, List<Teacher> teachers, List<FinalGrade> finalGrades, List<AttestationGrade> attestationGrades, Speciality speciality) {
        this.name = name;
        this.type = type;
        this.hours = hours;
        this.credits = credits;
        this.courseNumber = courseNumber;
        this.semesterNumber = semesterNumber;
        this.teachers = teachers;
        this.finalGrades = finalGrades;
        this.attestationGrades = attestationGrades;
        this.speciality = speciality;
    }

    public List<AttestationGrade> getAttestationGrades() {
        return attestationGrades;
    }

    public void setAttestationGrades(List<AttestationGrade> attestationGrades) {
        this.attestationGrades = attestationGrades;
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

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public List<FinalGrade> getFinalGrades() {
        return finalGrades;
    }

    public void setFinalGrades(List<FinalGrade> finalGrades) {
        this.finalGrades = finalGrades;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }
}
