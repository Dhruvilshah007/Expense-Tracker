package com.ds.expensetracker.expense.service;


import com.ds.expensetracker.common.constants.CommonConstants;
import com.ds.expensetracker.exception.commonException.ApplicationException;
import com.ds.expensetracker.expense.model.Expense;
import com.ds.expensetracker.expense.repository.ExpenseRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class ExpenseValidationService {

    private final ExpenseRepository expenseRepository;

    public Expense findByExpenseExists(long expensePkId) {
        return (Expense) expenseRepository.findByExpensePkIdAndActiveFlag(expensePkId, CommonConstants.ACTIVE_FLAG)
                .orElseThrow(() -> new ApplicationException(
                        HttpStatusCode.valueOf(404),
                        "Invalid Expense Id",
                        "The provided Expense Id does not exist or is invalid."
                ));
    }

    public void validateExpenseAndUser(Long expensePkId, String emailId) {
        boolean hasRights = expenseRepository.existsByExpensePkIdAndUserEmail(expensePkId, emailId);
        if (!hasRights) {
            throw new ApplicationException(
                    HttpStatusCode.valueOf(403),
                    "Unauthorized Access",
                    "You are not authorized to perform action on expense data"
            );
        }
    }

}