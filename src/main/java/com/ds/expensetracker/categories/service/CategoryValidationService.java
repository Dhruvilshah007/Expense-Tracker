package com.ds.expensetracker.categories.service;


import com.ds.expensetracker.cashbook.model.Cashbook;
import com.ds.expensetracker.categories.model.Categories;
import com.ds.expensetracker.categories.repository.CategoriesRepository;
import com.ds.expensetracker.exception.DuplicateEntityException;
import org.springframework.stereotype.Service;

@Service
public class CategoryValidationService {

    private final CategoriesRepository categoriesRepository;

    public CategoryValidationService(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    public void validateDuplicateCategory(String categoryName, Cashbook cashbook) {
        if (categoriesRepository.existsByCategoryNameAndCashbook(categoryName, cashbook)) {
            throw DuplicateEntityException.forEntity("Category", "Category Name", categoryName);
        }
    }

    public void validateUserToUpdateCatrgory(Categories category,String emailId) {
        if (!category.getCashbook().getUser().getEmailId().equals(emailId)) {
            throw new RuntimeException("User is not authorized to update this category");
        }
    }
}