package com.coursed.dto;

/**
 * Created by Hexray on 10.12.2016.
 */
public class TeacherDTO {

    public static class TeacherTitleDTO {
        private Long id;
        private String firstName;
        private String lastName;
        private String patronymic;

        public TeacherTitleDTO(){}

        public TeacherTitleDTO(Long id, String firstName, String lastName, String patronymic) {
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
    private String phoneNumber;

    public TeacherDTO() {
    }

    public TeacherDTO(Long id, String firstName, String lastName, String patronymic, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.phoneNumber = phoneNumber;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "TeacherDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

}
