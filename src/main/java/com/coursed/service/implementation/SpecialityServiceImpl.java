package com.coursed.service.implementation;

import com.coursed.dto.SpecialityDTO;
import com.coursed.model.Speciality;
import com.coursed.repository.SpecialityRepository;
import com.coursed.service.SpecialityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Hexray on 27.11.2016.
 */
@Service
public class SpecialityServiceImpl implements SpecialityService {
    @Autowired
    private SpecialityRepository specialityRepository;

    @Override
    public Speciality create(SpecialityDTO specialityDTO) {
        Speciality speciality = new Speciality();
        speciality.setFullName(specialityDTO.getFullName());
        speciality.setGroupsName(specialityDTO.getGroupsName());

        return specialityRepository.save(speciality);
    }

    @Override
    public List<Speciality> findAll() {
        return specialityRepository.findAll();
    }

    @Override
    public Speciality findOne(Long id) {
        return specialityRepository.findOne(id);
    }
}
