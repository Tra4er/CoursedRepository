package com.coursed.service;

import com.coursed.dto.SemesterDTO;
import com.coursed.model.Semester;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Hexray on 04.12.2016.
 */
public interface SemesterService {
    SemesterDTO getById(Long id);
    Page<SemesterDTO> getAll(int page, int size);
}
