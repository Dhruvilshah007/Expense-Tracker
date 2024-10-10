package com.ds.expensetracker.authentication.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PasswordResetToken", uniqueConstraints = {@UniqueConstraint(columnNames = "token")})
public class PasswordResetToken extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long passwordResetTokenPkId;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private String emailId;

    @Column(nullable = false)
    private Date expiryDate;
}

