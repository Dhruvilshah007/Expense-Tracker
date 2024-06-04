package com.ds.expensetracker.service;


import com.ds.expensetracker.dto.LoginUserDto;
import com.ds.expensetracker.dto.RegisterUserDto;
import com.ds.expensetracker.model.User;
import com.ds.expensetracker.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;


    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }


    public User register(RegisterUserDto registerUserDto){
        User user = new User();
        user .setName(registerUserDto.getName());
        user.setEmailId(registerUserDto.getEmailId());
        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));


        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto loginUserDto){
        authenticationManager.authenticate((new UsernamePasswordAuthenticationToken(loginUserDto.getEmailId(),loginUserDto.getPassword())));

        return userRepository.findByEmailId(loginUserDto.getEmailId()).orElseThrow();

    }


}
