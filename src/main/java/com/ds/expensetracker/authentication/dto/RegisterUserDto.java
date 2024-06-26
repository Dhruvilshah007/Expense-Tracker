package com.ds.expensetracker.authentication.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto {


    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Email Id is mandatory")
    @Email
    private String emailId;

    @NotBlank(message = "Password is mandatory")
    private String password;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthDate;

}
