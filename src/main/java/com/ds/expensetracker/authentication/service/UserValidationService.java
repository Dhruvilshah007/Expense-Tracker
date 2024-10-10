package com.ds.expensetracker.authentication.service;


import com.ds.expensetracker.authentication.model.User;
import com.ds.expensetracker.authentication.repository.UserRepository;
import com.ds.expensetracker.exception.commonException.ApplicationException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class UserValidationService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;


    public void validateOldPassword(String oldPasswordEntered, String oldPasswordInDb) {

        if(!passwordEncoder.matches(oldPasswordEntered, oldPasswordInDb)){
            throw new ApplicationException(
                    HttpStatusCode.valueOf(403),
                    "Incorrect old Password ",
                    "Old Password doesn't matches password stored"
            );
        }
    }

    public User validateEmailId(String emailId) {

        return userRepository.findByEmailId(emailId)
                .orElseThrow(() -> new ApplicationException(
                        HttpStatusCode.valueOf(404),
                        "Invalid Email Id",
                        "User not found"
                ));
    }
}
