package com.ds.expensetracker.authentication.controller;


import com.ds.expensetracker.authentication.dto.LoginResponse;
import com.ds.expensetracker.authentication.dto.LoginUserDto;
import com.ds.expensetracker.authentication.dto.RegisterUserDto;
import com.ds.expensetracker.authentication.model.User;
import com.ds.expensetracker.authentication.service.AuthenticationService;
import com.ds.expensetracker.authentication.service.BlacklistedTokenService;
import com.ds.expensetracker.authentication.util.JwtUtility;
import com.ds.expensetracker.common.response.GenericResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@AllArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    private final JwtUtility jwtUtility;

    private final AuthenticationService authenticationService;

    private final BlacklistedTokenService blacklistedTokenService;

    @PostMapping("/register")
    public ResponseEntity<GenericResponse> register(@RequestBody RegisterUserDto registerUserDto) {
        GenericResponse genericResponse = authenticationService.register(registerUserDto);
        return ResponseEntity.ok(genericResponse);
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

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String token = extractTokenFromRequest(request);
        LocalDateTime expirationTime= jwtUtility.getExpirationTime(token);
        blacklistedTokenService.addToBlacklist(token,expirationTime, request.getRemoteAddr());


        return ResponseEntity.ok("Logged out successfully");
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        // Get the Authorization header from the request
        String authorizationHeader = request.getHeader("Authorization");

        // Check if the Authorization header is not null and starts with "Bearer "
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            // Extract the JWT token (remove "Bearer " prefix)
            return authorizationHeader.substring(7);
        }
        // If the Authorization header is not valid, return null
        return null;
    }

    // // TODO: 09-06-2024 Forget Password
    // // // TODO: 09-06-2024 reset password


}