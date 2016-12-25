package com.coursed.repository;

import com.coursed.model.Discipline;
import com.coursed.model.Semester;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Hexray on 19.11.2016.
 */
@Repository
public interface DisciplineRepository extends CrudRepository<Discipline, Long> {
    List<Discipline> findAll();

    @Query("SELECT dis FROM " +
            "Discipline dis INNER JOIN dis.teachers " +
            "WHERE teacher_id = :teacherid")
    List<Discipline> getAllActualConnectedWithTeacher(@Param("teacherid")Long teacherId);

//    SELECT *FROM discipline_teachers LEFT JOIN discipline ON discipline_teachers.discipline_id = discipline.id
//    WHERE
//    discipline_teachers.teacher_id = (SELECT t.id FROM user u inner join teacher t ON u.teacher_id = t.id WHERE u.id = 2)
//    AND discipline.education_plan_id
//    IN (SELECT id
//    FROM education_plan
//            WHERE year_id = (SELECT year.id
//            FROM year left join semester
//            on year.id=semester.year_id
//            WHERE semester.id = (SELECT semester.id
//            FROM planned_event left join semester
//            ON planned_event.semester_id = semester.id
//            WHERE planned_event.id = 11)))
//    AND discipline.semester_number = (SELECT semester_number
//    FROM planned_event left join semester
//    ON planned_event.semester_id = semester.id
//    WHERE planned_event.id = 11);
}
