package com.ds.expensetracker.authentication.controller;


import com.ds.expensetracker.authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/auth/test")
    public String testGet(){
        return "testing";
    }

}
