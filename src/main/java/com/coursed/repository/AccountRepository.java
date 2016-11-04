package com.coursed.repository;

import com.coursed.entities.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Hexray on 16.10.2016.
 */

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
}