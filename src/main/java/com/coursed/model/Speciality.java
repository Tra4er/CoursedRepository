package com.coursed.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by Hexray on 14.11.2016.
 */
@Entity
public class Speciality {
    @Id
    @GeneratedValue
    private Long id;
    private String fullName;
    private String groupsName;

    @OneToMany(mappedBy = "speciality")
    private List<EdGroup> edGroups;

    @OneToMany(mappedBy = "speciality")
    private List<Discipline> disciplines;

    public Speciality() {
    }

    public Speciality(String fullName, String groupsName, List<EdGroup> edGroups, List<Discipline> disciplines) {
        this.fullName = fullName;
        this.groupsName = groupsName;
        this.edGroups = edGroups;
        this.disciplines = disciplines;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGroupsName() {
        return groupsName;
    }

    public void setGroupsName(String groupsName) {
        this.groupsName = groupsName;
    }

    public List<EdGroup> getEdGroups() {
        return edGroups;
    }

    public void setEdGroups(List<EdGroup> edGroups) {
        this.edGroups = edGroups;
    }

    public List<Discipline> getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(List<Discipline> disciplines) {
        this.disciplines = disciplines;
    }
}
