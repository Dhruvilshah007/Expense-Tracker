package com.ds.expensetracker.authentication.service;


import com.ds.expensetracker.exception.commonException.ApplicationException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class UserValidationService {

    private final PasswordEncoder passwordEncoder;


    public void validateOldPassword(String oldPasswordEntered, String oldPasswordInDb) {

        if(!passwordEncoder.matches(oldPasswordEntered, oldPasswordInDb)){
            throw new ApplicationException(
                    HttpStatusCode.valueOf(403),
                    "Incorrect old Password ",
                    "Old Password doesn't matches password stored"
            );
        }
    }
}
