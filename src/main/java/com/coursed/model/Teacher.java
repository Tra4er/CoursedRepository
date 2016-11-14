package com.coursed.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Hexray on 06.11.2016.
 */
@Entity
public class Teacher {
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private String patronymic;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private List<FinalGrade> finalGrades;

    @ManyToMany(mappedBy = "teachers", fetch = FetchType.LAZY)
    private List<Discipline> disciplines;

    public Teacher() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public List<FinalGrade> getFinalGrades() {
        return finalGrades;
    }

    public void setFinalGrades(List<FinalGrade> finalGrades) {
        this.finalGrades = finalGrades;
    }

    public List<Discipline> getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(List<Discipline> disciplines) {
        this.disciplines = disciplines;
    }
}
