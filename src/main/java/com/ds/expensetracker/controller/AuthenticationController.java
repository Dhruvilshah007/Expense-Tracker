package com.ds.expensetracker.controller;


import com.ds.expensetracker.dto.LoginResponse;
import com.ds.expensetracker.dto.LoginUserDto;
import com.ds.expensetracker.dto.RegisterUserDto;
import com.ds.expensetracker.model.User;
import com.ds.expensetracker.service.AuthenticationService;
import com.ds.expensetracker.util.JwtUtility;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    private final JwtUtility jwtUtility;

    private final AuthenticationService authenticationService;


    public AuthenticationController(JwtUtility jwtUtility, AuthenticationService authenticationService) {
        this.jwtUtility = jwtUtility;
        this.authenticationService = authenticationService;
    }


    @PostMapping("/register")
    public ResponseEntity<RegisterUserDto> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.register(registerUserDto);
        return ResponseEntity.ok(registerUserDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtUtility.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtUtility.getExpirationTime());


        return ResponseEntity.ok(loginResponse);
    }


}