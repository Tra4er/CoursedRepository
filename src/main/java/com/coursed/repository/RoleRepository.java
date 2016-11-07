package com.coursed.repository;

import com.coursed.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Hexray on 05.11.2016.
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
}
