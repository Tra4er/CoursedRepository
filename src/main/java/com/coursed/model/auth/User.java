package com.coursed.model.auth;


import com.coursed.model.Student;
import com.coursed.model.Teacher;

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

    @Column(nullable = false)
    private String password;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @OneToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    private Boolean isAStudent;
    private Boolean isATeacher;

    private Date registrationDate;

    @ManyToMany
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() {
    }

    public User(boolean enabled, String email, String password, Student student, Teacher teacher, Boolean isAStudent, Boolean isATeacher, Date registrationDate) {
        this.enabled = enabled;
        this.email = email;
        this.password = password;
        this.student = student;
        this.teacher = teacher;
        this.isAStudent = isAStudent;
        this.isATeacher = isATeacher;
        this.registrationDate = registrationDate;
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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Boolean getAStudent() {
        return isAStudent;
    }

    public void setAStudent(Boolean AStudent) {
        isAStudent = AStudent;
    }

    public Boolean getATeacher() {
        return isATeacher;
    }

    public void setATeacher(Boolean ATeacher) {
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
