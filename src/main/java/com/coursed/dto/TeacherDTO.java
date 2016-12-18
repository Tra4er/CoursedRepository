package com.coursed.dto;

/**
 * Created by Hexray on 10.12.2016.
 */
public class TeacherDTO {
    private String firstName;
    private String lastName;
    private String patronymic;

    public TeacherDTO() {
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
