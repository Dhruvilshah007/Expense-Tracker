package com.ds.expensetracker.cashbook.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CashbookDto {

    private long cashbookPkId;

    @NotBlank(message = "Cashbook name is mandatory")
    @Size(max = 200, message = "Cashbook name must not be greater than 200 characters")
    private String cashbookName;
}
