package com.coursed.dto;

import com.coursed.model.enums.StudentEducationStatus;

import java.util.Date;

/**
 * Created by Hexray on 04.12.2016.
 */
public class StudentDTO {

    public static class StudentTitleDTO {
        private Long id;
        private String firstName;
        private String lastName;
        private String patronymic;

        public StudentTitleDTO(){}

        public StudentTitleDTO(Long id, String firstName, String lastName, String patronymic) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.patronymic = patronymic;
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
    }

    private Long id;
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

    public StudentDTO(){}

    public StudentDTO(Long id, String firstName, String lastName, String patronymic) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
    }

    public StudentDTO(String firstName, String lastName, String patronymic, String address, String gradeBookNumber,
                      Date birthDate, StudentEducationStatus studentEducationStatus, Boolean isBudgetStudent,
                      String additionalInformation, String parentsInfo) {
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

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public StudentEducationStatus getStudentEducationStatus() {
        return studentEducationStatus;
    }

    public void setStudentEducationStatus(StudentEducationStatus studentEducationStatus) {
        this.studentEducationStatus = studentEducationStatus;
    }

}
