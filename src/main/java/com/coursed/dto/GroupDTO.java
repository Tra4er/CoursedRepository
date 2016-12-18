package com.coursed.dto;

import com.coursed.model.enums.CourseNumber;
import com.coursed.model.enums.GroupDegree;
import com.coursed.model.enums.GroupType;

/**
 * Created by Hexray on 04.12.2016.
 */
public class GroupDTO {
    private Integer number;
    private GroupType groupType;
    private GroupDegree groupDegree;
    private CourseNumber courseNumber;
    private Long semesterId;
    private Long specialityId;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public void setGroupType(GroupType groupType) {
        this.groupType = groupType;
    }

    public GroupDegree getGroupDegree() {
        return groupDegree;
    }

    public void setGroupDegree(GroupDegree groupDegree) {
        this.groupDegree = groupDegree;
    }

    public CourseNumber getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(CourseNumber courseNumber) {
        this.courseNumber = courseNumber;
    }

    public Long getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(Long semesterId) {
        this.semesterId = semesterId;
    }

    public Long getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(Long specialityId) {
        this.specialityId = specialityId;
    }
}
