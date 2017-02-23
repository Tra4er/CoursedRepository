package com.coursed.controller.mvc;

import com.coursed.dto.SpecialityDTO;
import com.coursed.dto.UserTeacherDTO;
import com.coursed.dto.YearDTO;
import com.coursed.model.*;
import com.coursed.model.auth.Role;
import com.coursed.model.auth.User;
import com.coursed.model.enums.*;
import com.coursed.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @Autowired
    private PlannedEventService plannedEventService;

    @Autowired
    private SemesterService semesterService;

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

        UserTeacherDTO user_Zhdanova = new UserTeacherDTO();
        user_Zhdanova.setEmail("head@head.com");
        user_Zhdanova.setPassword("123");
        user_Zhdanova.setPatronymic("Григорівна");
        user_Zhdanova.setPhoneNumber("+380938524565");
        user_Zhdanova.setLastName("Жданова");
        user_Zhdanova.setFirstName("Олена");

        User zhd = userService.registerTeacher(user_Zhdanova);
        userService.connectUserWithRole(zhd, headRole);


        //Year and semesters
        YearDTO yearDTO = new YearDTO();
        yearDTO.setBeginYear(2015);
        yearDTO.setEndYear(2016);
        yearService.create(yearDTO);

        //Specialities
        SpecialityDTO is = new SpecialityDTO("Комп'ютерні науки", "ІС");
        SpecialityDTO pi = new SpecialityDTO("Програмна інженерія", "ІП");
//        Speciality isSp = specialityService.create(is); // TODO
//        Speciality piSp = specialityService.create(pi);
        Speciality isSp = new Speciality();
        Speciality piSp = new Speciality();


        //Groups
        Semester fifthSemester = yearService.findOne(1L).getSemesters().get(1);
        Group is43 = new Group(43, GroupType.GENERAL_FORM, GroupDegree.BACHELOR, CourseNumber.THIRD, fifthSemester, isSp);
        Group is42 = new Group(42, GroupType.GENERAL_FORM, GroupDegree.BACHELOR, CourseNumber.THIRD, fifthSemester, isSp);
        Group is41 = new Group(41, GroupType.GENERAL_FORM, GroupDegree.BACHELOR, CourseNumber.THIRD, fifthSemester, isSp);

        groupService.create(is43);
        groupService.create(is42);
        groupService.create(is41);

        //Planned Events
        PlannedEvent event1 = null;
        PlannedEvent event2 = null;
            event1 = new PlannedEvent("2016-10-08T12:30:00", "2016-11-08T18:00:00",
                    PlannedEventType.ATTESTATION_FIRST, semesterService.findOne(1L));
            event2 = new PlannedEvent("2017-04-08T12:30:00", "2017-07-08T18:00:00",
                    PlannedEventType.ATTESTATION_FIRST, semesterService.findOne(1L));
        plannedEventService.create(event1);
        plannedEventService.create(event2);

        System.out.println("==>Base configuration has been loaded");
        return "redirect:/login";
    }
}
