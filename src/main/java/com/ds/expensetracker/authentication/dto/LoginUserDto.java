package com.ds.expensetracker.authentication.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDto {

    private String emailId;

    private String password;
}
