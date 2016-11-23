package com.coursed.service;

import com.coursed.model.Year;
import com.coursed.repository.YearRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Created by Hexray on 16.11.2016.
 */
@Service
public class YearServiceImpl implements YearService {
    @Autowired
    private YearRepository yearRepository;

    @Override
    public Set<Year> findAll() {
        return yearRepository.findAll();
    }
}
