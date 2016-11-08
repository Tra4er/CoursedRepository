package com.coursed.model;


import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

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

    private Boolean isStudent;
    private Boolean isTeacher;
    private Boolean isEmailConfirmed;
    private Boolean isRoleConfirmed;
    private Date registrationDate;


    @ManyToMany
    @JoinTable(name = "account_roles", joinColumns = @JoinColumn(name = "account_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setStudent(Boolean student) {
        isStudent = student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Boolean teacher) {
        isTeacher = teacher;
    }

    public Boolean getEmailConfirmed() {
        return isEmailConfirmed;
    }

    public void setEmailConfirmed(Boolean emailConfirmed) {
        isEmailConfirmed = emailConfirmed;
    }

    public Boolean getRoleConfirmed() {
        return isRoleConfirmed;
    }

    public void setRoleConfirmed(Boolean roleConfirmed) {
        isRoleConfirmed = roleConfirmed;
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

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
