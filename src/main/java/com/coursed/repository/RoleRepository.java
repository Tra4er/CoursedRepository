package com.coursed.repository;

import com.coursed.dto.RoleDTO;
import com.coursed.model.auth.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Hexray on 05.11.2016.
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    List<Role> findAll();
    Role findByName(String name);

    @Query("SELECT new com.coursed.dto.RoleDTO(r.id, r.name) FROM Role r")
    List<RoleDTO> findAllInDTO();
}
