package com.ds.expensetracker.categories.service;


import com.ds.expensetracker.authentication.model.User;
import com.ds.expensetracker.authentication.repository.UserRepository;
import com.ds.expensetracker.authentication.util.UserUtility;
import com.ds.expensetracker.cashbook.model.Cashbook;
import com.ds.expensetracker.cashbook.service.CashbookValidationService;
import com.ds.expensetracker.categories.dto.CategoryDto;
import com.ds.expensetracker.categories.model.Categories;
import com.ds.expensetracker.categories.repository.CategoriesRepository;
import com.ds.expensetracker.common.constants.CommonConstants;
import com.ds.expensetracker.common.response.GenericResponse;
import com.ds.expensetracker.exception.commonException.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoriesService {

    //Constructor Injection instead of Autowired
    private final CashbookValidationService cashbookValidationService;
    private final CategoryValidationService categoryValidationService;
    private final CategoriesRepository categoriesRepository;
    private final UserRepository userRepository;

    public GenericResponse createCategory(CategoryDto categoryDto, String clientIpAddress) {

        // Validate cashbook exists
        Cashbook cashbook = cashbookValidationService.validateCashbookExists(categoryDto.getCashbookPkId());

        // Validate user exists and has relation with cashbook
        User user = UserUtility.getCurrentUser();
        cashbookValidationService.validateUserHasRelationWithCashbook(cashbook.getCashbookPkId(), user);

        // Validate duplicate category
        categoryValidationService.validateDuplicateCategory(categoryDto.getCategoryName(), cashbook);

        // Proceed with creating the category
        Categories categories = new Categories();
        BeanUtils.copyProperties(categoryDto, categories);
        categories.setCashbook(cashbook);
        categories.setCreatedByIpaddress(clientIpAddress);

        Categories savedCategory = categoriesRepository.save(categories);

        BeanUtils.copyProperties(savedCategory, categoryDto);

        return new GenericResponse(CommonConstants.SUCCESS_STATUS, "Category " + CommonConstants.CREATED, savedCategory);
    }


    public GenericResponse updateCategory(long categoryPkId, CategoryDto categoryDto, String clientIpAddress) {

        //validate if Category with PkId exists
        Categories existingCategory = (Categories) categoriesRepository.findByCategoryPkIdAndActiveFlag(categoryPkId, CommonConstants.ACTIVE_FLAG).orElseThrow(() -> new ApplicationException(HttpStatusCode.valueOf(404), "Not Found", "Category not found"));

        //validate Category ownership to Update
        String emailId  = UserUtility.getCurrentUserEmail();
        categoryValidationService.validateUserAndCategory(existingCategory, emailId);

        // Validate duplicate category w.r.t Cashbook
        categoryValidationService.validateDuplicateCategory(categoryDto.getCategoryName(), existingCategory.getCashbook());

        //update category
        existingCategory.setCategoryName(categoryDto.getCategoryName());
        existingCategory.setUpdatedByIpaddress(clientIpAddress);
        Categories updatedCategory = categoriesRepository.save(existingCategory);

        return new GenericResponse(CommonConstants.SUCCESS_STATUS, "Category " + CommonConstants.UPDATED, updatedCategory);

    }

    public GenericResponse deleteCategory(long categoryPkId, String clientIpAddress) {

        //validate if Category with PkId exists
        Categories category = (Categories) categoriesRepository.findByCategoryPkIdAndActiveFlag(categoryPkId, CommonConstants.ACTIVE_FLAG).orElseThrow(() -> new ApplicationException(HttpStatusCode.valueOf(404), "Not Found", "Category not found"));

        //validate Category ownership to Delete
        String emailId  = UserUtility.getCurrentUserEmail();
        categoryValidationService.validateUserAndCategory(category, emailId);

        category.setActiveFlag(CommonConstants.INACTIVE_FLAG);
        category.setUpdatedByIpaddress(clientIpAddress);
        Categories deletedCategory = categoriesRepository.save(category);

        return new GenericResponse(CommonConstants.SUCCESS_STATUS, "Category " + CommonConstants.DELETED, deletedCategory);
    }

    public Categories getCategory(long categoryPkId) {

        //validate if Category with PkId exists
        Categories category = (Categories) categoriesRepository.findByCategoryPkIdAndActiveFlag(categoryPkId, CommonConstants.ACTIVE_FLAG).orElseThrow(() -> new ApplicationException(HttpStatusCode.valueOf(404), "Not Found", "Category not found"));

        //validate Category ownership
        String emailId  = UserUtility.getCurrentUserEmail();
        categoryValidationService.validateUserAndCategory(category, emailId);

        return category;
    }
}
