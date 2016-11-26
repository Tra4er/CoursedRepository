package com.coursed.repository;

import com.coursed.model.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Hexray on 19.11.2016.
 */
@Repository
public interface GroupRepository extends CrudRepository<Group, Long> {
    List<Group> findAll();
}
