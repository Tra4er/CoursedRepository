package com.coursed.service.implementation;

import com.coursed.model.auth.User;
import com.coursed.model.auth.VerificationToken;
import com.coursed.repository.VerificationTokenRepository;
import com.coursed.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Trach on 1/3/2017.
 */
public class VerificationTokenServiceImpl implements VerificationTokenService {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Override
    public void create(VerificationToken token) {
        verificationTokenRepository.save(token);
    }

    @Override
    public VerificationToken getById(Long id) {
        return verificationTokenRepository.findOne(id);
    }

    @Override
    public VerificationToken getByToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    @Override
    public User getUserByToken(String token) { // TODO
        return verificationTokenRepository.findByToken(token).getUser();
    }

    @Override
    public VerificationToken getByUser(User user) {
        return verificationTokenRepository.findByUser(user);
    }
}
