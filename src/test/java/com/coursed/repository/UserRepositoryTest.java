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
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void getAllUnconfirmedTeachers(){
        userRepository.findAllUnconfirmedTeachers().stream().forEach(System.out::println);
    }

}
