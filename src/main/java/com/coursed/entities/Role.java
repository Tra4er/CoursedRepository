package com.coursed.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Hexray on 04.11.2016.
 */
public class Role {
    @Id
    @GeneratedValue
    private Long id;

    private String roleName;
}
