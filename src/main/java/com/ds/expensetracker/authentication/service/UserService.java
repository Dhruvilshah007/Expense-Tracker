package com.ds.expensetracker.authentication.service;

import com.ds.expensetracker.authentication.dto.ResetPasswordDto;
import com.ds.expensetracker.authentication.model.User;
import com.ds.expensetracker.authentication.repository.UserRepository;
import com.ds.expensetracker.authentication.util.UserUtility;
import com.ds.expensetracker.common.constants.CommonConstants;
import com.ds.expensetracker.common.response.GenericResponse;
import com.ds.expensetracker.exception.commonException.ApplicationException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
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

    private final PasswordResetTokenService passwordResetTokenService;


    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }

    public void resetPassword(ResetPasswordDto resetPasswordDto, String remoteAddr) {

        User user = UserUtility.getCurrentUser();

        //validate Oldpassword is same as one Stored in DB
        userValidationService.validateOldPassword(resetPasswordDto.getOldPassword(), user.getPassword());

        user.setPassword(passwordEncoder.encode(resetPasswordDto.getNewPassword()));

        userRepository.save(user);

    }

    public User updateUser(User user, String remoteAddr) {
        User updateUser = userRepository.findByEmailId(user.getEmailId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        updateUser.setName(user.getName());
        updateUser.setBirthDate(user.getBirthDate());
        updateUser.setUpdatedByIpaddress(remoteAddr);
        userRepository.save(user);
        return updateUser;
    }

    public String forgotPassword(String emailId) {

        //validte email Id exists or not
        User user = userValidationService.validateEmailId(emailId);

        //Save token into Token-User_id-expiration table
        String token = passwordResetTokenService.saveToken(user);

        //Send Email Service
        String resetPasswordUrl = "http://localhost:8080/users/createNewPassword?token=" + token;

        return resetPasswordUrl;
    }

    public GenericResponse createNewPassword(ResetPasswordDto resetPasswordDto, String token) {

        //CHeck If token is Valid
        // If valid Get Userdetails
        //If not valid Throw exception -> Invalid, expired etcc...

        String emailId = passwordResetTokenService.isTokenValid(token);

        //Get user details from Email and Than Update Password in DB
        User user = userValidationService.validateEmailId(emailId);

        //Also check If both password are same or not
        if(!resetPasswordDto.getNewPassword().equals(resetPasswordDto.getConfirmNewPassword())){
            throw new ApplicationException(
                    HttpStatusCode.valueOf(403),
                    "Password doesn't match",
                    "New password and Confirm password doesn't match"
            );
        }

        user.setPassword(passwordEncoder.encode(resetPasswordDto.getNewPassword()));
        userRepository.save(user);

        return new GenericResponse(CommonConstants.SUCCESS_STATUS, "New Password Created Successfully", "");


    }
}