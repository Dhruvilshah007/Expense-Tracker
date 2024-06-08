package com.ds.expensetracker.expense.controller;


import com.ds.expensetracker.common.response.GenericResponse;
import com.ds.expensetracker.expense.dto.ExpenseDto;
import com.ds.expensetracker.expense.model.Expense;
import com.ds.expensetracker.expense.service.ExpenseService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/expense")
@RestController
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;


    @PostMapping("/createExpense")
    public ResponseEntity<GenericResponse> createExpense(@RequestBody ExpenseDto expenseDto, HttpServletRequest request) {
        GenericResponse genericResponse = expenseService.createExpense(expenseDto, request.getRemoteAddr());
        return ResponseEntity.ok(genericResponse);
    }

    @GetMapping("/getExpense")
    public ResponseEntity<Expense> getExpense(@RequestParam long expensePkId) {
        Expense expense = expenseService.getExpense(expensePkId);
        return ResponseEntity.ok(expense);
    }

    @PutMapping("/updateExpense")
    public ResponseEntity<GenericResponse> updateExpense(@RequestParam long expensePkId, @RequestBody ExpenseDto expenseDto, HttpServletRequest request) {
        GenericResponse genericResponse = expenseService.updateExpense(expensePkId, expenseDto, request.getRemoteAddr());
        return ResponseEntity.ok(genericResponse);
    }

    @DeleteMapping("/deleteExpense")
    public ResponseEntity<GenericResponse> deleteExpense(@RequestParam long expensePkId, HttpServletRequest request) {
        GenericResponse genericResponse = expenseService.deleteExpense(expensePkId, request.getRemoteAddr());
        return ResponseEntity.ok(genericResponse);
    }

}
