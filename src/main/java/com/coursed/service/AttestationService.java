package com.coursed.service;

import com.coursed.dto.AttestationDTO;
import com.coursed.model.AttestationGrade;

import java.util.List;

/**
 * Created by Hexray on 19.12.2016.
 */
public interface AttestationService {
    void createManyFirst(List<AttestationDTO> attestationDTOList);

}
