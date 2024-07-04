package com.ds.expensetracker.categories.service;


import com.ds.expensetracker.cashbook.model.Cashbook;
import com.ds.expensetracker.categories.model.Categories;
import com.ds.expensetracker.categories.repository.CategoriesRepository;
import com.ds.expensetracker.common.constants.CommonConstants;
import com.ds.expensetracker.exception.commonException.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryValidationService {
    private final CategoriesRepository categoriesRepository;

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
                    "You are not authorized to Perform action on Category"
            );
        }
    }

    public Categories validateCategoryExists(Long categoryPkId) {
        return (Categories) categoriesRepository.findByCategoryPkIdAndActiveFlag(categoryPkId, CommonConstants.ACTIVE_FLAG)
                .orElseThrow(() -> new ApplicationException(
                        HttpStatusCode.valueOf(404),
                        "Invalid Category Id",
                        "The provided Category Id does not exist or is invalid."
                ));
    }
}