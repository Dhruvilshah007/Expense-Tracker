package com.ds.expensetracker.authentication.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResetPasswordDto {

    private String oldPassword;

    private String newPassword;

    private String confirmNewPassword;
}
