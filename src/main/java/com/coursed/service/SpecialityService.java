package com.coursed.service;

import com.coursed.dto.SpecialityDTO;
import com.coursed.model.Speciality;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Hexray on 26.11.2016.
 */
public interface SpecialityService {
    SpecialityDTO create(SpecialityDTO specialityDTO);
    Page<SpecialityDTO> getAll(int page, int size);
    SpecialityDTO getById(Long specialityId);
}
