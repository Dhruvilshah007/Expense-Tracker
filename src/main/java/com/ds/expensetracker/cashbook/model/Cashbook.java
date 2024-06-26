package com.ds.expensetracker.cashbook.model;


import com.ds.expensetracker.authentication.model.BaseEntity;
import com.ds.expensetracker.authentication.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Cashbooks", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id","cashbookName"})})
public class    Cashbook  extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //1,2,3,4
    @NotNull
    private long cashbookPkId;

    @NotNull
    @Size(max = 200, message = "Cashbook name must not be greater than 200 characters")
    private String cashbookName;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
}
