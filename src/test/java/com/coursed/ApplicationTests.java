package com.coursed;

import com.coursed.model.Teacher;
import com.coursed.repository.GroupRepository;
import com.coursed.repository.TeacherRepository;
import com.coursed.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Hexray on 25.12.2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    UserRepository userRepository;

    @Test
    public void contextLoads() {
        System.out.println("Result: ");
        System.out.println(userRepository.findOneByStudentInDTO(2L));
//        for(Object[] items : teacherRepository.findM()) {
//            String t = (String)items[0];
//            System.out.println(t);
//        }
    }

}