package com.ds.expensetracker.categories.service;


import com.ds.expensetracker.cashbook.model.Cashbook;
import com.ds.expensetracker.categories.model.Categories;
import com.ds.expensetracker.categories.repository.CategoriesRepository;
import com.ds.expensetracker.exception.commonException.ApplicationException;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

@Service
public class CategoryValidationService {

    private final CategoriesRepository categoriesRepository;

    public CategoryValidationService(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    public void validateDuplicateCategory(String categoryName, Cashbook cashbook) {
        if (categoriesRepository.existsByCategoryNameAndCashbook(categoryName, cashbook)) {
            throw new ApplicationException(
                    HttpStatusCode.valueOf(409 ),
                    "Duplicate Category Name",
                    "Category with Category Name - " + categoryName + " already exists"
            );
        }
    }

    public void validateUserAndCategory(Categories category, String emailId) {
        if (!category.getCashbook().getUser().getEmailId().equals(emailId)) {
            throw new ApplicationException(
                    HttpStatusCode.valueOf(403 ),
                    "Unauthorized Access",
                    "You are not authorized to Update Category"
            );
        }
    }

}