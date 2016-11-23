package com.coursed.repository;

import com.coursed.model.EdGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Hexray on 19.11.2016.
 */
@Repository
public interface EdGroupRepository extends CrudRepository<EdGroup, Long> {
}
