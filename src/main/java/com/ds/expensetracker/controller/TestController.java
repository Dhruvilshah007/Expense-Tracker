package com.ds.expensetracker.controller;


import com.ds.expensetracker.repository.UserRepository;
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
