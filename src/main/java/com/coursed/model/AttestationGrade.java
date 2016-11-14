package com.coursed.model;

import javax.persistence.*;

/**
 * Created by Hexray on 15.11.2016.
 */
@Entity
public class AttestationGrade {
    @Id
    @GeneratedValue
    private Long id;
    private Boolean firstTry;
    private Boolean secondTry;

    @ManyToOne
    @JoinColumn(name="student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name="discipline_id")
    private Discipline discipline;

    public AttestationGrade(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getFirstTry() {
        return firstTry;
    }

    public void setFirstTry(Boolean firstTry) {
        this.firstTry = firstTry;
    }

    public Boolean getSecondTry() {
        return secondTry;
    }

    public void setSecondTry(Boolean secondTry) {
        this.secondTry = secondTry;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }
}
