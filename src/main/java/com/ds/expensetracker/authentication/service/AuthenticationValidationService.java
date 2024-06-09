package com.ds.expensetracker.authentication.service;


import com.ds.expensetracker.authentication.repository.UserRepository;
import com.ds.expensetracker.exception.commonException.ApplicationException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthenticationValidationService {

    private final UserRepository userRepository;


    public void checkIfEmailIdExists(String emailId) {

        if (!userRepository.findByEmailId(emailId).isEmpty()) {
            throw new ApplicationException(
                    HttpStatusCode.valueOf(409 ),
                    "EmailId already exists ",
                    "EmailId - " + emailId + " is already registered.Please different EmailId"
            );
        }

    }
}
