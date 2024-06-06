package com.ds.expensetracker.cashbook.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashbookDto<T>{

    private String status;
    private String message;

    private T data;

}
