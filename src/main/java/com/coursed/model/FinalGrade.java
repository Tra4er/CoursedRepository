package com.coursed.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Hexray on 14.11.2016.
 */
@Entity
public class FinalGrade {
    @Id
    @GeneratedValue
    private Long id;
    private Float value;
    private Date gradeDate;

    private Boolean isExam;
    private Float firstTry;
    private Float secondTry;
    private Float thirdTry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="teacher_id")
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="discipline_id")
    private Discipline discipline;

}
