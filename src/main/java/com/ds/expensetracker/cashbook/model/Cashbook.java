package com.ds.expensetracker.cashbook.model;


import com.ds.expensetracker.authentication.model.BaseEntity;
import com.ds.expensetracker.authentication.model.User;
import com.ds.expensetracker.categories.model.Categories;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Cashbooks", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id","cashbookName"})})
public class Cashbook  extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //1,2,3,4
    @NotNull
    private long cashbookPkId;

    @NotNull
    private String cashbookName;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;


    @JsonIgnore
    @OneToMany(mappedBy = "cashbook", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Categories> categoriesList;


}
