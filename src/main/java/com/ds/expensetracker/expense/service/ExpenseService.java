package com.ds.expensetracker.expense.service;


import com.ds.expensetracker.authentication.model.User;
import com.ds.expensetracker.authentication.repository.UserRepository;
import com.ds.expensetracker.authentication.util.UserUtility;
import com.ds.expensetracker.cashbook.model.Cashbook;
import com.ds.expensetracker.cashbook.service.CashbookValidationService;
import com.ds.expensetracker.categories.model.Categories;
import com.ds.expensetracker.categories.repository.CategoriesRepository;
import com.ds.expensetracker.categories.service.CategoryValidationService;
import com.ds.expensetracker.common.constants.CommonConstants;
import com.ds.expensetracker.common.response.GenericResponse;
import com.ds.expensetracker.expense.dto.ExpenseDto;
import com.ds.expensetracker.expense.model.Expense;
import com.ds.expensetracker.expense.repository.ExpenseRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
@AllArgsConstructor
public class ExpenseService {

    private final CashbookValidationService cashbookValidationService;
    private final CategoryValidationService categoryValidationService;
    private final ExpenseValidationService expenseValidationService;
    private final CategoriesRepository categoriesRepository;
    private final UserRepository userRepository;
    private final ExpenseRepository expenseRepository;


    public GenericResponse createExpense(ExpenseDto expenseDto, String clientIpAddress) {

        //check if CashbookPkId Exists
        Cashbook cashbook = cashbookValidationService.validateCashbookExists(expenseDto.getCashbookPkId());

        //check if CategoryPkId exists
        Categories category = categoryValidationService.validateCategoryExists(expenseDto.getCategoryPkId());

        User user = UserUtility.getCurrentUser();
        //validate User has relation with Cashbook to add it in expense
        cashbookValidationService.validateCashbookOwnership(cashbook.getCashbookPkId(), user.getEmailId());

        //validate if user has relation with Category to add it in expense
        categoryValidationService.validateUserAndCategory(category, user.getEmailId());


        Expense expense=new Expense();
        BeanUtils.copyProperties(expenseDto,expense);
        expense.setCreatedByIpaddress(clientIpAddress);
        expense.setCashbook(cashbook);
        expense.setCategory(category);

        Expense savedExpense=expenseRepository.save(expense);
        
        return new GenericResponse(CommonConstants.SUCCESS_STATUS, "Expense " + CommonConstants.CREATED, savedExpense);
    }

    public Expense getExpense(long expensePkId) {

        //validate if current User has rights to see expense, as it current user's expense
        String emailId = UserUtility.getCurrentUserEmail();
        expenseValidationService.validateExpenseAndUser(expensePkId,emailId);

        //validate If expense exists
        Expense expense=expenseValidationService.findByExpenseExists(expensePkId);

        return expense;
    }


    public GenericResponse updateExpense(long expensePkId, ExpenseDto expenseDto, String clientIpAddress) {

        //validate if current User has rights to see expense, as it current user's expense
        String emailId = UserUtility.getCurrentUserEmail();
        expenseValidationService.validateExpenseAndUser(expensePkId,emailId);

        //validate If expense exists
        Expense expense=expenseValidationService.findByExpenseExists(expensePkId);

        //check if CashbookPkId Exists
        Cashbook cashbook = cashbookValidationService.validateCashbookExists(expenseDto.getCashbookPkId());

        //check if CategoryPkId exists
        Categories category = categoryValidationService.validateCategoryExists(expenseDto.getCategoryPkId());

        User user = UserUtility.getCurrentUser();
        //validate User has relation with Cashbook to add it in expense
        cashbookValidationService.validateCashbookOwnership(cashbook.getCashbookPkId(), user.getEmailId());

        //validate if user has relation with Category to add it in expense
        categoryValidationService.validateUserAndCategory(category, user.getEmailId());


        String[] ignoreProperties = {"expensePkId"};
        BeanUtils.copyProperties(expenseDto,expense,ignoreProperties);
        expense.setUpdatedByIpaddress(clientIpAddress);
        expense.setUpdatedDate(new Date());

        return new GenericResponse(CommonConstants.SUCCESS_STATUS, "Expense " + CommonConstants.UPDATED, expense);
    }

    public GenericResponse deleteExpense(long expensePkId, String clientIpAddress) {

        //validate if current User has rights to see expense, as it current user's expense
        String emailId = UserUtility.getCurrentUserEmail();
        expenseValidationService.validateExpenseAndUser(expensePkId,emailId);

        //validate If expense exists
        Expense expense=expenseValidationService.findByExpenseExists(expensePkId);

        //check if CashbookPkId Exists
        Cashbook cashbook = cashbookValidationService.validateCashbookExists(expense.getCashbook().getCashbookPkId());

        //check if CategoryPkId exists
        Categories category = categoryValidationService.validateCategoryExists(expense.getCategory().getCategoryPkId());

        User user = UserUtility.getCurrentUser();
        //validate User has relation with Cashbook to add it in expense
        cashbookValidationService.validateCashbookOwnership(cashbook.getCashbookPkId(), user.getEmailId());

        //validate if user has relation with Category to add it in expense
        categoryValidationService.validateUserAndCategory(category, user.getEmailId());

        expense.setUpdatedByIpaddress(clientIpAddress);
        expense.setUpdatedDate(new Date());
        expense.setActiveFlag(CommonConstants.INACTIVE_FLAG);

        Expense deletedExpense=expenseRepository.save(expense);

        return new GenericResponse(CommonConstants.SUCCESS_STATUS, "Expense " + CommonConstants.DELETED, expense);
    }
}
