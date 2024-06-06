package com.ds.expensetracker.categories.dto;

import com.ds.expensetracker.authentication.model.BaseEntity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto extends BaseEntity {

    private long categoryPkId;

    @NotNull
    private String categoryName;


    @NotNull
    private Long cashbookPkId;
}
