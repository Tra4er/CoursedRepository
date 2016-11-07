package com.coursed.repository;

import com.coursed.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Hexray on 16.10.2016.
 * Edited by Trach on 07.11.2016
 */

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Query("select u FROM User u WHERE u.email=:email")
    User findByEmail(@Param("email")String email);
}