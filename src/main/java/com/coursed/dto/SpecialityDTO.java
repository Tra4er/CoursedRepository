package com.coursed.dto;

/**
 * Created by Hexray on 13.12.2016.
 */
public class SpecialityDTO {

    private Long id;
    private String fullName;
    private String groupsName;

    public SpecialityDTO() {
    }

    public SpecialityDTO(Long id, String fullName, String groupsName) {
        this.id = id;
        this.fullName = fullName;
        this.groupsName = groupsName;
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

    public SpecialityDTO(String fullName, String groupsName) {
        this.fullName = fullName;
        this.groupsName = groupsName;
    }

}
