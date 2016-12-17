package com.coursed.model;

import com.coursed.model.enums.CourseNumber;
import com.coursed.model.enums.GroupDegree;
import com.coursed.model.enums.GroupType;
import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Hexray on 13.11.2016.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Group.class)
@Entity(name = "edgroup")
public class Group {
    @Id
    @GeneratedValue
    private Long id;
    private Integer number;
    @Enumerated
    private GroupType groupType;
    @Enumerated
    private GroupDegree groupDegree;
    @Enumerated
    private CourseNumber courseNumber;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="semester_id")
    private Semester semester;

    @JsonIgnore
    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private List<Student> students;

    @ManyToOne
    @JoinColumn(name="speciality_id")
    private Speciality speciality;

    @ManyToMany
    @JoinTable(name = "curators_groups", joinColumns = @JoinColumn(name = "edgroup_id"), inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    private List<Teacher> curators;

    public Group() {
    }

    public Group(Integer number, GroupType groupType, GroupDegree groupDegree, CourseNumber courseNumber, Semester semester, Speciality speciality) {
        this.number = number;
        this.groupType = groupType;
        this.groupDegree = groupDegree;
        this.courseNumber = courseNumber;
        this.semester = semester;
        this.speciality = speciality;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public List<Teacher> getCurators() {
        return curators;
    }

    public void setCurators(List<Teacher> curators) {
        this.curators = curators;
    }
}
