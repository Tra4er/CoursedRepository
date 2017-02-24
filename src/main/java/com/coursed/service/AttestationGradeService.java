package com.coursed.service;

import com.coursed.dto.AttestationGradeDTO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Hexray on 19.12.2016.
 */
public interface AttestationGradeService {
    AttestationGradeDTO getById(Long attestationGradeId);
    Page<AttestationGradeDTO> getAll(int page, int size);
    void createManyFirst(List<AttestationGradeDTO> attestationGradeDTOList);

}
