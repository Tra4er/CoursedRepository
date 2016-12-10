package com.coursed.controller.mvc;

import com.coursed.dto.TeacherRegistrationForm;
import com.coursed.dto.UserTeacherRegistrationForm;
import com.coursed.model.Group;
import com.coursed.model.Semester;
import com.coursed.model.Speciality;
import com.coursed.model.Year;
import com.coursed.model.auth.Role;
import com.coursed.model.auth.User;
import com.coursed.model.enums.CourseNumber;
import com.coursed.model.enums.GroupDegree;
import com.coursed.model.enums.GroupType;
import com.coursed.model.enums.SemesterNumber;
import com.coursed.repository.SemesterRepository;
import com.coursed.repository.YearRepository;
import com.coursed.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

/**
 * Created by Hexray on 31.10.2016.
 */
@Controller
public class BaseInitController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private YearService yearService;

    @Autowired
    private SpecialityService specialityService;

    @Autowired
    private GroupService groupService;

    @Transactional
    @RequestMapping("/valve")
    public String index() {
        //Roles
        Role registeredRole = new Role("ROLE_REGISTERED");
        Role studentRole = new Role("ROLE_STUDENT");
        Role teacherRole = new Role("ROLE_TEACHER");

        Role headRole = new Role("ROLE_HEAD");
        Role secretaryRole = new Role("ROLE_SECRETARY");
        Role educatorRole = new Role("ROLE_EDUCATOR");

        roleService.create(registeredRole);
        roleService.create(studentRole);
        roleService.create(teacherRole);

        roleService.create(headRole);
        roleService.create(secretaryRole);
        roleService.create(educatorRole);
        //Users

        UserTeacherRegistrationForm user_Zhdanova = new UserTeacherRegistrationForm();
        user_Zhdanova.setEmail("head@head.com");
        user_Zhdanova.setPassword("123");
        user_Zhdanova.setPatronymic("Григорівна");
        user_Zhdanova.setLastName("Жданова");
        user_Zhdanova.setFirstName("Олена");

        User zhd = userService.registerTeacher(user_Zhdanova);
        userService.connectUserWithRole(zhd, headRole);



        //Year and semesters
        Year year = new Year(2015, 2016);
        yearService.create(year);

        //Specialities
        Speciality is = new Speciality("Комп'ютерні науки", "ІС");
        Speciality pi = new Speciality("Програмна інженерія", "ІП");
        specialityService.create(is);
        specialityService.create(pi);


        //Groups
        Semester fifthSemester = yearService.findOne(1L).getSemesters().get(1);
        Group is43 = new Group(43, GroupType.GENERAL_FORM, GroupDegree.BACHELOR, CourseNumber.THIRD, fifthSemester, is);
        Group is42 = new Group(42, GroupType.GENERAL_FORM, GroupDegree.BACHELOR, CourseNumber.THIRD, fifthSemester, is);
        Group is41 = new Group(41, GroupType.GENERAL_FORM, GroupDegree.BACHELOR, CourseNumber.THIRD, fifthSemester, is);

        groupService.create(is43);
        groupService.create(is42);
        groupService.create(is41);

        System.out.println("==>Base configuration has been loaded");
        return "redirect:/login";
    }
}
