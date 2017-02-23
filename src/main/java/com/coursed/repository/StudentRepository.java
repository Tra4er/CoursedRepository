package com.coursed.repository;

import com.coursed.dto.StudentDTO;
import com.coursed.dto.TeacherDTO;
import com.coursed.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Hexray on 19.11.2016.
 */
@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {

//    @Query("select new com.coursed.dto.StudentDTO(s.id, s.firstName, s.lastName, s.patronymic, s.phoneNumber, " +
//            "s.address, s.gradeBookNumber, s.birthDate, s.studentEducationStatus, s.isBudgetStudent, " +
//            "s.additionalInformation, s.parentsInfo, s.group.id) " +
//            "from Student s WHERE s.id = ?1")

    @Query("SELECT new com.coursed.dto.StudentDTO(s.id, s.firstName, s.lastName, s.patronymic) " +
            "FROM Student s WHERE s.id = ?1") // TODO add more fields like in the comment upper
    StudentDTO findOneInDTO(Long studentId);

    @Query("SELECT new com.coursed.dto.StudentDTO$StudentTitleDTO(s.id, s.firstName, s.lastName, s.patronymic) " +
            "FROM Student s")
    Page<StudentDTO.StudentTitleDTO> findAllInDTO(Pageable pageable);

    @Query("SELECT new com.coursed.dto.StudentDTO$StudentTitleDTO(s.id, s.firstName, s.lastName, s.patronymic) " +
            "FROM Student s LEFT JOIN s.group g WHERE g.id = ?1")
    Page<StudentDTO.StudentTitleDTO> findAllByGroupInDTO(Long groupId, Pageable pageable);
}
