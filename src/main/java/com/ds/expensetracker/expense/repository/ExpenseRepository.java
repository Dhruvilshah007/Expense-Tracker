package com.ds.expensetracker.expense.repository;

import com.ds.expensetracker.expense.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Long> {

    Optional<Object> findByExpensePkIdAndActiveFlag(long expensePkId, int activeFlag);


    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Expense e " +
            "JOIN e.cashbook c " +
            "JOIN c.user u " +
            "WHERE e.expensePkId = :expensePkId AND u.emailId = :emailId")
    boolean existsByExpensePkIdAndUserEmail(@Param("expensePkId") Long expensePkId, @Param("emailId") String emailId);


}
