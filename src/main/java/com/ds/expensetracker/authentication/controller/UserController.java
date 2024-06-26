package com.ds.expensetracker.authentication.controller;


import com.ds.expensetracker.authentication.dto.ResetPasswordDto;
import com.ds.expensetracker.authentication.model.User;
import com.ds.expensetracker.authentication.repository.UserRepository;
import com.ds.expensetracker.authentication.service.UserService;
import com.ds.expensetracker.exception.commonException.ApplicationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String emailId = authentication.getPrincipal().toString();
            User currentUser = userRepository.findByEmailId(emailId).get();
            return ResponseEntity.ok(currentUser);
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> allUsers() {
        List<User> users = userService.allUsers();

        return ResponseEntity.ok(users);
    }


    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto, HttpServletRequest request) {

        //check If both New passoword are same of not
        if (!resetPasswordDto.getNewPassword().equals(resetPasswordDto.getConfirmNewPassword())) {
            throw new ApplicationException(
                    HttpStatusCode.valueOf(403),
                    "New password doesnt Matches",
                    "New pasword and Confirm new Password doesnt match"
            );
        }

        userService.resetPassword(resetPasswordDto, request.getRemoteAddr());
        return ResponseEntity.ok("Password Reset Successfully");
    }


    @PutMapping
    public ResponseEntity<?> updateUser(
            User user, HttpServletRequest request
    ) {
        user = userService.updateUser(user, request.getRemoteAddr());
        return ResponseEntity.ok(user);
    }

    // // TODO: 09-06-2024 Forget Password

}
