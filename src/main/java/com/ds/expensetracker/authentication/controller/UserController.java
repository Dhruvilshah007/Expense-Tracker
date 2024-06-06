package com.ds.expensetracker.authentication.controller;


import com.ds.expensetracker.authentication.model.User;
import com.ds.expensetracker.authentication.repository.UserRepository;
import com.ds.expensetracker.authentication.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService,UserRepository userRepository) {
        this.userService = userService;
        this.userRepository=userRepository;
    }

    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {

        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String emailId=authentication.getPrincipal().toString();
            User currentUser = userRepository.findByEmailId(emailId).get();
            return ResponseEntity.ok(currentUser);
        }catch(Exception e){
            throw e;
        }
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> allUsers() {
        List<User> users = userService.allUsers();

        return ResponseEntity.ok(users);
    }
}
