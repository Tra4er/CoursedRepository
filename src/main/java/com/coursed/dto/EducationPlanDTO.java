package com.coursed.dto;

import com.coursed.model.enums.CourseNumber;
import com.coursed.model.enums.GroupDegree;
import com.coursed.model.enums.GroupType;

/**
 * Created by Hexray on 11.12.2016.
 */
public class EducationPlanDTO {
    private Long yearId;
    private Long specialityId;

    private GroupType groupType;
    private GroupDegree groupDegree;
    private CourseNumber courseNumber;

    public EducationPlanDTO() {
    }

    public Long getYearId() {
        return yearId;
    }

    public void setYearId(Long yearId) {
        this.yearId = yearId;
    }

    public Long getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(Long specialityId) {
        this.specialityId = specialityId;
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
}
