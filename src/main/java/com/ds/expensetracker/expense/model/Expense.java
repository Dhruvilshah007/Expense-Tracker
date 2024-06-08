package com.ds.expensetracker.expense.model;

import com.ds.expensetracker.authentication.model.BaseEntity;
import com.ds.expensetracker.cashbook.model.Cashbook;
import com.ds.expensetracker.categories.model.Categories;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
@Entity
@Table(name = "Expenses")
public class Expense extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //1,2,3,4
    @NotNull
    private long expensePkId;

    @NotBlank(message = "Entry Type is mandatory")
    private String entryType;

    @NotNull
    private Date entryDateTime;


    @NotNull
    private BigDecimal amount;

    private String remarks;

    @NotBlank(message = "Payment Mode is mandatory")
    private String paymentMode;

    // TODO: Add multiple attachment Field

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cashbook_id")
    private Cashbook cashbook;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Categories category;


}
