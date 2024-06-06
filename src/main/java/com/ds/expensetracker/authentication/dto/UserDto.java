package com.ds.expensetracker.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String name;

    private String emailId;

    private Date birthDate;
}
