package com.ds.expensetracker.categories.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto{

    private long categoryPkId;


    @NotBlank(message = "Category name is mandatory")
    private String categoryName;


    @NotNull
    private Long cashbookPkId;
}
