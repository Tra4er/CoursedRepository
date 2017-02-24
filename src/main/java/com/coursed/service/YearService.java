package com.coursed.service;

import com.coursed.dto.YearDTO;
import com.coursed.model.Year;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Hexray on 13.11.2016.
 */
public interface YearService {
    YearDTO getById(Long yearId);
    Year create(YearDTO yearDTO);
    Page<YearDTO> getAll(int page, int size);
    YearDTO getCurrent();
}
