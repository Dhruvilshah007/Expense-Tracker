package com.ds.expensetracker.expense.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDto {

    private long expensePkId;

    @NotBlank(message = "Entry Type is mandatory")
    private String entryType;

    @NotNull
    private Date entryDateTime;


    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private BigDecimal amount;

    private String remarks;

    @NotBlank(message = "Payment Mode is mandatory")
    private String paymentMode;


    @NotNull
    private Long cashbookPkId;

    @NotNull
    private Long categoryPkId;
}
