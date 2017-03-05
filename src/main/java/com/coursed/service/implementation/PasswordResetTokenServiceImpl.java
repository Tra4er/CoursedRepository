package com.coursed.service.implementation;

import com.coursed.dto.PasswordDTO;
import com.coursed.error.exception.InvalidPasswordResetTokenException;
import com.coursed.model.auth.PasswordResetToken;
import com.coursed.model.auth.User;
import com.coursed.repository.PasswordResetTokenRepository;
import com.coursed.repository.UserRepository;
import com.coursed.service.PasswordResetTokenService;
import com.coursed.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

/**
 * Created by Trach on 12/31/2016.
 */
@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordResetTokenServiceImpl.class);

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public void resetPassword(PasswordDTO passwordDTO) {
        String token = passwordDTO.getToken();
        LOGGER.debug("Validating password reset token: " + token);
        PasswordResetToken passToken = passwordResetTokenRepository.findByToken(token);
        if ((passToken == null)) {
            throw new InvalidPasswordResetTokenException("InvalidToken");
        }

        Calendar cal = Calendar.getInstance();
        if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            throw new InvalidPasswordResetTokenException("Expired");
        }

        User user = userRepository.findOneByPasswordResetTokenInDTO(token);

        userService.changeUserPassword(user, passwordDTO.getNewPassword());
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
