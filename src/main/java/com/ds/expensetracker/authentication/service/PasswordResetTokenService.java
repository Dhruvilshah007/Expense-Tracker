package com.ds.expensetracker.authentication.service;


import com.ds.expensetracker.authentication.model.PasswordResetToken;
import com.ds.expensetracker.authentication.model.User;
import com.ds.expensetracker.authentication.repository.PasswordResetTokenRepository;
import com.ds.expensetracker.common.constants.CommonConstants;
import com.ds.expensetracker.exception.commonException.ApplicationException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;


@AllArgsConstructor
@Service
public class PasswordResetTokenService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public String saveToken(User user) {

        // Validate if User has requested 3 times a day for password chnage, throw validation 3 time s max


        //Create One token
        String resetToken = UUID.randomUUID().toString();

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setEmailId(user.getEmailId());
        passwordResetToken.setToken(resetToken);
        passwordResetToken.setExpiryDate(getExpirationDate());
        //setipaddress

        PasswordResetToken savedPasswordResetToken=passwordResetTokenRepository.save(passwordResetToken);
        return savedPasswordResetToken.getToken();
    }


    private static Date getExpirationDate() {
        LocalDateTime now = LocalDateTime.now();

        // Add 15 minutes to the current date and time
        LocalDateTime newDateTime = now.plusMinutes(15);

        // Convert LocalDateTime to Date
        ZonedDateTime zonedDateTime = newDateTime.atZone(ZoneId.systemDefault());
        Date expirationDate = Date.from(zonedDateTime.toInstant());

        return expirationDate;
    }


    public String isTokenValid(String token) {

        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findActiveToken(token);

        if (passwordResetToken == null) {
            throw new ApplicationException(
                    HttpStatusCode.valueOf(404),
                    "Invalid token",
                    "The token has expired or not found"
            );
        }

        //Deactivating active flag
        passwordResetToken.setActiveFlag(CommonConstants.INACTIVE_FLAG);
        passwordResetToken.setUpdatedDate(new Date());
        passwordResetTokenRepository.save(passwordResetToken);

        return passwordResetToken.getEmailId();
    }
}
