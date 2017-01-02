package com.coursed.task;

import com.coursed.repository.PasswordResetTokenRepository;
import com.coursed.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;

/**
 * Created by Trach on 1/2/2017.
 */
@Service
@Transactional
public class TokensPurgeTask {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;

    @Scheduled(cron = "${purge.cron.expression}")
    public void purgeExpired() {

        Date now = Date.from(Instant.now());

        passwordTokenRepository.deleteAllExpiredSince(now);
//        verificationTokenRepository.deleteAllExpiredAndActivatedSince(now); // TODO
    }
}
