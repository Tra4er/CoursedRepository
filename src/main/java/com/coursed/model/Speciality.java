package com.coursed.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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

//    @JsonBackReference("group-speciality")
    @OneToMany(mappedBy = "speciality")
    private List<Group> groups;

//    @JsonIgnore
    //@JsonManagedReference("speciality-educationplan")
    @OneToMany(mappedBy = "speciality")
    private List<EducationPlan> educationPlans;

    public Speciality() {
    }

    public Speciality(String fullName, String groupsName) {
        this.fullName = fullName;
        this.groupsName = groupsName;
    }

    public List<EducationPlan> getEducationPlans() {
        return educationPlans;
    }

    public void setEducationPlans(List<EducationPlan> educationPlans) {
        this.educationPlans = educationPlans;
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

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
