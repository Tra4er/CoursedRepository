package com.coursed.dto;

import com.coursed.model.enums.StudentEducationStatus;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

/**
 * Created by Hexray on 10.12.2016.
 */
public class UserStudentRegistrationForm {
    private String firstName;
    private String lastName;
    private String patronymic;
    private String address;
    private String gradeBookNumber;
    private Date birthDate;
    private StudentEducationStatus studentEducationStatus;
    private Boolean isBudgetStudent;
    private String additionalInformation;
    private String parentsInfo;
    private Long groupId;
    //phoneNumber Todo

    private String email;

    private String password;

    private String confirmPassword;

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

    public void setBudgetStudent(Boolean isBudgetStudent) {
        this.isBudgetStudent = isBudgetStudent;
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

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
