package com.coursed.dto;

import com.coursed.model.enums.StudentEducationStatus;

import java.util.Date;

/**
 * Created by Hexray on 10.12.2016.
 */
public class UserStudentDTO extends CaptchaDTO implements BasicPersonDTO {
    private String firstName;
    private String lastName;
    private String patronymic;
    private String phoneNumber;
    private String address;
    private String gradeBookNumber;
    private Date birthDate;
    private StudentEducationStatus studentEducationStatus;
    private String isBudgetStudent;
    private String additionalInformation;
    private String parentsInfo;
    private Long groupId;

//    TODO
    private Long semesterId;
    private Long specialityId;
    private Long yearId;

    private String email;

    private String password;

    private String confirmPassword;

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getPatronymic() {
        return patronymic;
    }

    @Override
    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getIsBudgetStudent() {
        return isBudgetStudent;
    }

    public void setIsBudgetStudent(String isBudgetStudent) {
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

    public Long getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(Long semesterId) {
        this.semesterId = semesterId;
    }

    public Long getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(Long specialityId) {
        this.specialityId = specialityId;
    }

    public Long getYearId() {
        return yearId;
    }

    public void setYearId(Long yearId) {
        this.yearId = yearId;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getConfirmPassword() {
        return confirmPassword;
    }

    @Override
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
