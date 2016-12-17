package com.coursed.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Hexray on 14.11.2016.
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
@Entity
public class FinalGrade {
    @Id
    @GeneratedValue
    private Long id;
    private Float value;
    private Date gradeDate;

    private Boolean isExam;
    private Float firstTry;
    private Float secondTry;
    private Float thirdTry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="teacher_id")
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="discipline_id")
    private Discipline discipline;

    public FinalGrade(){

    }

    public FinalGrade(Float value, Date gradeDate, Boolean isExam, Float firstTry, Float secondTry, Float thirdTry, Student student, Teacher teacher, Discipline discipline) {
        this.value = value;
        this.gradeDate = gradeDate;
        this.isExam = isExam;
        this.firstTry = firstTry;
        this.secondTry = secondTry;
        this.thirdTry = thirdTry;
        this.student = student;
        this.teacher = teacher;
        this.discipline = discipline;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Date getGradeDate() {
        return gradeDate;
    }

    public void setGradeDate(Date gradeDate) {
        this.gradeDate = gradeDate;
    }

    public Boolean getExam() {
        return isExam;
    }

    public void setExam(Boolean exam) {
        isExam = exam;
    }

    public Float getFirstTry() {
        return firstTry;
    }

    public void setFirstTry(Float firstTry) {
        this.firstTry = firstTry;
    }

    public Float getSecondTry() {
        return secondTry;
    }

    public void setSecondTry(Float secondTry) {
        this.secondTry = secondTry;
    }

    public Float getThirdTry() {
        return thirdTry;
    }

    public void setThirdTry(Float thirdTry) {
        this.thirdTry = thirdTry;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }
}
