package com.coursed.model;

import javax.persistence.*;
import java.util.Set;


/**
 * Created by Hexray on 13.11.2016.
 */
@Entity
public class Year {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private Integer beginYear;

    @OneToMany(mappedBy = "year")
    private Set<Semester> semesters;

    public Year() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBeginYear() {
        return beginYear;
    }

    public void setBeginYear(Integer beginYear) {
        this.beginYear = beginYear;
    }

    public Set<Semester> getSemesters() {
        return semesters;
    }

    public void setSemesters(Set<Semester> semesters) {
        this.semesters = semesters;
    }
}
