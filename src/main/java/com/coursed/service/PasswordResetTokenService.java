package com.coursed.service;

import com.coursed.dto.PasswordDTO;
import com.coursed.model.auth.PasswordResetToken;
import com.coursed.model.auth.User;

/**
 * Created by Trach on 12/31/2016.
 */
public interface PasswordResetTokenService {
    void resetPassword(PasswordDTO passwordDTO);
    PasswordResetToken getById(Long id);
    PasswordResetToken getByToken(String token);
    User getUserByToken(String token);
}
