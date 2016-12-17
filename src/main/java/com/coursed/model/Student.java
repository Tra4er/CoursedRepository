package com.coursed.model;

import com.coursed.model.auth.User;
import com.coursed.model.enums.StudentEducationStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Hexray on 06.11.2016.
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
@Entity
public class Student {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "studentEntity")
    private User user;

    private String firstName;
    private String lastName;
    private String patronymic;
    private String address;
    private String gradeBookNumber;
    private Date birthDate;
    @Enumerated
    private StudentEducationStatus studentEducationStatus;
    private Boolean isBudgetStudent;

    private String additionalInformation;
    private String parentsInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="edgroup_id")
    private Group group;

    @OneToMany(mappedBy = "student")
    private List<FinalGrade> finalGrades;

    @OneToMany(mappedBy = "student")
    private List<AttestationGrade> attestationGrades;

    public Student() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Student(String firstName, String lastName, String patronymic, String address, String gradeBookNumber, Date birthDate, StudentEducationStatus studentEducationStatus, Boolean isBudgetStudent, String additionalInformation, String parentsInfo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.address = address;
        this.gradeBookNumber = gradeBookNumber;
        this.birthDate = birthDate;
        this.studentEducationStatus = studentEducationStatus;
        this.isBudgetStudent = isBudgetStudent;
        this.additionalInformation = additionalInformation;
        this.parentsInfo = parentsInfo;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGradeBookNumber() {
        return gradeBookNumber;
    }

    public void setGradeBookNumber(String gradeBookNumber) {
        this.gradeBookNumber = gradeBookNumber;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public StudentEducationStatus getStudentEducationStatus() {
        return studentEducationStatus;
    }

    public void setStudentEducationStatus(StudentEducationStatus studentEducationStatus) {
        this.studentEducationStatus = studentEducationStatus;
    }

    public Boolean getBudgetStudent() {
        return isBudgetStudent;
    }

    public void setBudgetStudent(Boolean budgetStudent) {
        isBudgetStudent = budgetStudent;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public String getParentsInfo() {
        return parentsInfo;
    }

    public void setParentsInfo(String parentsInfo) {
        this.parentsInfo = parentsInfo;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<FinalGrade> getFinalGrades() {
        return finalGrades;
    }

    public void setFinalGrades(List<FinalGrade> finalGrades) {
        this.finalGrades = finalGrades;
    }

    public List<AttestationGrade> getAttestationGrades() {
        return attestationGrades;
    }

    public void setAttestationGrades(List<AttestationGrade> attestationGrades) {
        this.attestationGrades = attestationGrades;
    }
}
