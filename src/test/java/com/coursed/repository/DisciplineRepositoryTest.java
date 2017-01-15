package com.coursed.repository;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Hexray on 25.12.2016.
 */
@SpringBootTest
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class DisciplineRepositoryTest {

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Test
    public void getAllActualDisciplinesConnectedWithTeacher(){
//        System.out.println("=============All");
//        disciplineRepository.getAllCuratorsOfGroup().stream().forEach(System.out::println);
//        System.out.println("=============NEXT");

        Long teacherId = 4L;
        Long plannedEventId = 11L;
        disciplineRepository.getAllActualConnectedWithTeacher(teacherId)
        .stream().forEach(System.out::println);

//        System.out.println("=============NEXT");
//        disciplineRepository.getAllConnectedWithTeacher().stream().forEach(System.out::println);
    }

}