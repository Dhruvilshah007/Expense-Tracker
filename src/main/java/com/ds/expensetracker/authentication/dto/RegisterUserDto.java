package com.ds.expensetracker.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto {

    private String name;

    private String emailId;

    private String password;

    private Date birthDate;



}
