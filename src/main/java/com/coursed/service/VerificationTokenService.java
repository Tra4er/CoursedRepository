package com.coursed.service;

import com.coursed.model.auth.User;
import com.coursed.model.auth.VerificationToken;

/**
 * Created by Trach on 1/3/2017.
 */
public interface VerificationTokenService {
    void create(VerificationToken token);
    VerificationToken getById(Long id);
    VerificationToken getByToken(String token);
    User getUserByToken(String token);
    VerificationToken getByUser(User user);
}
