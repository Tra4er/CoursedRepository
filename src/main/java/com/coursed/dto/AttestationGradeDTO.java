package com.coursed.dto;

/**
 * Created by Hexray on 19.12.2016.
 */
public class AttestationGradeDTO {

    private Long id;
    private String firstTry;
    private String secondTry;
    private Long studentId;
    private Long disciplineId;

    public AttestationGradeDTO(Long id, Boolean firstTry, Boolean secondTry, Long studentId, Long disciplineId) {
        this.id = id;
        this.firstTry = Boolean.toString(firstTry);
        this.secondTry = Boolean.toString(secondTry);
        this.studentId = studentId;
        this.disciplineId = disciplineId;
    }

    public AttestationGradeDTO(Long id, String firstTry, String secondTry, Long studentId, Long disciplineId) {
        this.id = id;
        this.firstTry = firstTry;
        this.secondTry = secondTry;
        this.studentId = studentId;
        this.disciplineId = disciplineId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstTry() {
        return firstTry;
    }

    public void setFirstTry(String firstTry) {
        this.firstTry = firstTry;
    }

    public String getSecondTry() {
        return secondTry;
    }

    public void setSecondTry(String secondTry) {
        this.secondTry = secondTry;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(Long disciplineId) {
        this.disciplineId = disciplineId;
    }
}
