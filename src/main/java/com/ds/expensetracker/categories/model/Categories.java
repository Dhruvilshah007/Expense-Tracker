package com.ds.expensetracker.categories.model;

import com.ds.expensetracker.authentication.model.BaseEntity;
import com.ds.expensetracker.cashbook.model.Cashbook;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Categories", uniqueConstraints = {@UniqueConstraint(columnNames = {"cashbook_id","categoryName"})})
public class Categories extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //1,2,3,4
    @NotNull
    private long categoryPkId;

    @NotNull
    private String categoryName;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cashbook_id")
    private Cashbook cashbook;
}
