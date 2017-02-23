package com.coursed.service.implementation;

import com.coursed.dto.SpecialityDTO;
import com.coursed.model.Speciality;
import com.coursed.repository.SpecialityRepository;
import com.coursed.service.SpecialityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public SpecialityDTO create(SpecialityDTO specialityDTO) {
        Speciality speciality = new Speciality();
        speciality.setFullName(specialityDTO.getFullName());
        speciality.setGroupsName(specialityDTO.getGroupsName());
        Speciality savedSpeciality = specialityRepository.save(speciality);

        return specialityRepository.findOneInDTO(savedSpeciality.getId());
    }

    @Override
    public Page<SpecialityDTO> getAll(int page, int size) {
        return specialityRepository.findAllInDTO(new PageRequest(page, size));
    }

    @Override
    public SpecialityDTO getById(Long specialityId) {
        return specialityRepository.findOneInDTO(specialityId);
    }
}
