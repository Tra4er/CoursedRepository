package com.coursed.dto;

/**
 * Created by Hexray on 13.12.2016.
 */
public class SpecialityForm {
    private String fullName;
    private String groupsName;

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

    public SpecialityForm(String fullName, String groupsName) {
        this.fullName = fullName;
        this.groupsName = groupsName;
    }

    public SpecialityForm() {
    }
}
