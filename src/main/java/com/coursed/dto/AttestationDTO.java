package com.coursed.dto;

/**
 * Created by Hexray on 19.12.2016.
 */
public class AttestationDTO {
    private String firstTry;
    private String secondTry;
    private Long studentId;
    private Long disciplineId;

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
