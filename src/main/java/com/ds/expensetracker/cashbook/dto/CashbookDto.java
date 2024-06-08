package com.ds.expensetracker.cashbook.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CashbookDto{

    private long cashbookPkId;

    @NotBlank(message = "Cashbook name is mandatory")
    private String cashbookName;
}
