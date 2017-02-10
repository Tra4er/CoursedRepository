package com.coursed.repository;

import com.coursed.model.auth.User;
import com.coursed.model.auth.VerificationToken;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Trach on 12/3/2016.
 */
@Repository
@Transactional
public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);

    Stream<VerificationToken> findAllByExpiryDateLessThan(Date now);

    void deleteByExpiryDateLessThan(Date now);

    @Modifying
    @Query("delete from VerificationToken t where t.expiryDate <= ?1")
    void deleteAllExpiredSince(Date now);

    @Modifying
    @Query("delete from VerificationToken t where t.expiryDate <= ?1 and t.user = (" +
            "select u from User u where u = t.user and u.enabled = true)")
    void deleteAllExpiredAndActivatedSince(Date now);
}
