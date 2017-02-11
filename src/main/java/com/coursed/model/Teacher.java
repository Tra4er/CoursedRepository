package com.coursed.model;

import com.coursed.model.auth.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @JsonBackReference("user-teacher")
    @OneToOne(mappedBy = "teacherEntity", fetch = FetchType.LAZY)
    private User user;

    private String firstName;
    private String lastName;
    private String patronymic;
    private String phoneNumber;

    @JsonIgnore
    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private List<FinalGrade> finalGrades;
    @JsonIgnore
    @ManyToMany(mappedBy = "teachers", fetch = FetchType.LAZY)
    private List<Discipline> disciplines;

    @JsonIgnore
    @ManyToMany(mappedBy = "curators", fetch = FetchType.LAZY)
    private List<Group> curatedGroups;

    public Teacher() {
    }

    public Teacher(String firstName, String lastName, String patronymic, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.phoneNumber = phoneNumber;
    }

    public Teacher(String firstName, String lastName, String patronymic, List<FinalGrade> finalGrades, List<Discipline> disciplines) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.finalGrades = finalGrades;
        this.disciplines = disciplines;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public List<Group> getCuratedGroups() {
        return curatedGroups;
    }

    public void setCuratedGroups(List<Group> curatedGroups) {
        this.curatedGroups = curatedGroups;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", user=" + user +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", finalGrades=" + finalGrades +
                ", disciplines=" + disciplines +
                ", curatedGroups=" + curatedGroups +
                '}';
    }
}
