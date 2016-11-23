package com.coursed.repository;

import com.coursed.model.PlannedEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Hexray on 19.11.2016.
 */
@Repository
public interface PlannedEventRepository extends CrudRepository<PlannedEvent, Long> {
}
