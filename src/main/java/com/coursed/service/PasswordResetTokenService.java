package com.coursed.service;

import com.coursed.model.auth.PasswordResetToken;
import com.coursed.model.auth.User;

/**
 * Created by Trach on 12/31/2016.
 */
public interface PasswordResetTokenService {
    void create(PasswordResetToken token);
    PasswordResetToken getById(Long id);
    PasswordResetToken getByToken(String token);
    User getUserByToken(String token);
}
