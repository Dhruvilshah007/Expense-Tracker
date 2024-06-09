package com.ds.expensetracker.authentication.service;

import com.ds.expensetracker.authentication.dto.ResetPasswordDto;
import com.ds.expensetracker.authentication.model.User;
import com.ds.expensetracker.authentication.repository.UserRepository;
import com.ds.expensetracker.authentication.util.UserUtility;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    private final UserValidationService userValidationService;

    private final PasswordEncoder passwordEncoder;


    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }

    public void resetPassword(ResetPasswordDto resetPasswordDto, String remoteAddr) {

        User user= UserUtility.getCurrentUser();

        //validate Oldpassword is same as one Stored in DB
        userValidationService.validateOldPassword(resetPasswordDto.getOldPassword(),user.getPassword());

        user.setPassword(passwordEncoder.encode(resetPasswordDto.getNewPassword()));

        userRepository.save(user);

    }
}