package com.coursed.model.auth;


import com.coursed.model.Student;
import com.coursed.model.Teacher;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    private Student studentEntity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teacher_id")
    private Teacher teacherEntity;
    @JsonIgnore
    private boolean isAStudent;
    @JsonIgnore
    private boolean isATeacher;
    @JsonIgnore
    private Date registrationDate;


    @ManyToMany
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Student getStudentEntity() {
        return studentEntity;
    }

    public void setStudentEntity(Student studentEntity) {
        this.studentEntity = studentEntity;
    }

    public Teacher getTeacherEntity() {
        return teacherEntity;
    }

    public void setTeacherEntity(Teacher teacherEntity) {
        this.teacherEntity = teacherEntity;
    }

    public boolean isAStudent() {
        return isAStudent;
    }

    public void setAsStudent(boolean AStudent) {
        isAStudent = AStudent;
    }

    public boolean isATeacher() {
        return isATeacher;
    }

    public void setAsTeacher(boolean ATeacher) {
        isATeacher = ATeacher;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
