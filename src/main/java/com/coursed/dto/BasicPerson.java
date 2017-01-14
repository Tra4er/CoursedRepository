package com.coursed.dto;

/**
 * Created by Trach on 12/16/2016.
 */
public interface BasicPerson {
    String getEmail();

    void setEmail(String email);

    String getPassword();

    void setPassword(String password);

    String getConfirmPassword();

    void setConfirmPassword(String confirmPassword);

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    String getPatronymic();

    void setPatronymic(String patronymic);

    void setPhoneNumber(String phoneNumber);

    String getPhoneNumber();
}
