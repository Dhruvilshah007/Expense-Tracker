package com.ds.expensetracker.authentication.util;

import com.ds.expensetracker.authentication.model.User;
import com.ds.expensetracker.authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserUtility {

    private static UserRepository userRepository;

    @Autowired
    public UserUtility(UserRepository userRepository) {
        UserUtility.userRepository = userRepository;
    }

    public static String getCurrentUserEmail() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static User getCurrentUser() {
        String emailId = getCurrentUserEmail();
        return userRepository.findByEmailId(emailId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}