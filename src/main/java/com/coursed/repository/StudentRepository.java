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

    @Query("select new com.coursed.dto.StudentDTO(s.id, s.firstName, s.lastName, s.patronymic) " +
            "from Student s WHERE s.id = ?1") // TODO add more fields like in the comment upper
    StudentDTO findOneInDTO(Long id);

    @Query("select new com.coursed.dto.StudentDTO$StudentTitleDTO(s.id, s.firstName, s.lastName, s.patronymic) " +
            "from Student s")
    Page<StudentDTO.StudentTitleDTO> findAllInDTO(Pageable pageable);
}
