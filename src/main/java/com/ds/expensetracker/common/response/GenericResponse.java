package com.ds.expensetracker.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenericResponse<T>{

    private String status;
    private String message;

    private T data;

}
