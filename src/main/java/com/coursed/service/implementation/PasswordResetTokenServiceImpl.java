package com.coursed.service.implementation;

import com.coursed.model.auth.PasswordResetToken;
import com.coursed.model.auth.User;
import com.coursed.repository.PasswordResetTokenRepository;
import com.coursed.service.PasswordResetTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Trach on 12/31/2016.
 */
@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public void create(PasswordResetToken token) {
        passwordResetTokenRepository.save(token);
    }

    @Override
    public PasswordResetToken getById(Long id) {
        return passwordResetTokenRepository.findOne(id);
    }

    @Override
    public PasswordResetToken getByToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    @Override
    public User getUserByToken(String token) { // TODO
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        return passwordResetToken.getUser();
    }
}
