package com.coursed.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Hexray on 06.11.2016.
 */
@Entity
public class Student {

    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String patronymic;

}
