package com.ds.expensetracker.authentication.service;


import com.ds.expensetracker.authentication.dto.LoginUserDto;
import com.ds.expensetracker.authentication.dto.RegisterUserDto;
import com.ds.expensetracker.authentication.model.User;
import com.ds.expensetracker.authentication.repository.BlacklistedTokenRepository;
import com.ds.expensetracker.authentication.repository.UserRepository;
import com.ds.expensetracker.common.constants.CommonConstants;
import com.ds.expensetracker.common.response.GenericResponse;
import com.ds.expensetracker.exception.commonException.ApplicationException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final BlacklistedTokenRepository blacklistedTokenRepository;

    private final AuthenticationValidationService authenticationValidationService;


    public GenericResponse register(RegisterUserDto registerUserDto){

        //Validate if same email Address comes
        authenticationValidationService.checkIfEmailIdExists(registerUserDto.getEmailId());

        User user = new User();
        user.setName(registerUserDto.getName());
        user.setEmailId(registerUserDto.getEmailId());
        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));

        User registeredUser=userRepository.save(user);

        return new GenericResponse(CommonConstants.SUCCESS_STATUS, "User " + CommonConstants.CREATED, registeredUser);
    }

    public User authenticate(LoginUserDto loginUserDto){
        authenticationManager.authenticate((new UsernamePasswordAuthenticationToken(loginUserDto.getEmailId(),loginUserDto.getPassword())));

        return userRepository.findByEmailId(loginUserDto.getEmailId()).orElseThrow(() -> new ApplicationException(
                HttpStatusCode.valueOf(404),
                "Invalid Email Id",
                "The provided Email Id does not exist or is invalid."
        ));
    }


}
