package com.ds.expensetracker.categories.service;


import com.ds.expensetracker.authentication.model.User;
import com.ds.expensetracker.authentication.repository.UserRepository;
import com.ds.expensetracker.cashbook.model.Cashbook;
import com.ds.expensetracker.cashbook.service.CashbookValidationService;
import com.ds.expensetracker.categories.dto.CategoryDto;
import com.ds.expensetracker.categories.model.Categories;
import com.ds.expensetracker.categories.repository.CategoriesRepository;
import com.ds.expensetracker.common.constants.CommonConstants;
import com.ds.expensetracker.common.response.GenericResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoriesService {

    //Constructor Injection instead of Autowired
    private final CashbookValidationService cashbookValidationService;
    private final CategoryValidationService categoryValidationService;
    private final CategoriesRepository categoriesRepository;
    private final UserRepository userRepository;

    public CategoriesService(CashbookValidationService cashbookValidationService, CategoryValidationService categoryValidationService, CategoriesRepository categoriesRepository, UserRepository userRepository) {
        this.cashbookValidationService = cashbookValidationService;
        this.categoryValidationService = categoryValidationService;
        this.categoriesRepository = categoriesRepository;
        this.userRepository = userRepository;
    }

    public GenericResponse createCategory(CategoryDto categoryDto) {

        // Validate cashbook exists
        Cashbook cashbook = cashbookValidationService.validateCashbookExists(categoryDto.getCashbookPkId());

        // Validate user exists and has relation with cashbook
        String emailId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmailId(emailId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        cashbookValidationService.validateUserHasRelationWithCashbook(cashbook.getCashbookPkId(), user);

        // Validate duplicate category
        categoryValidationService.validateDuplicateCategory(categoryDto.getCategoryName(), cashbook);

        // Proceed with creating the category
        Categories categories = new Categories();
        BeanUtils.copyProperties(categoryDto, categories);
        categories.setCashbook(cashbook);

        Categories savedCategory = categoriesRepository.save(categories);

        return new GenericResponse(CommonConstants.SUCCESS_STATUS, "Category " + CommonConstants.CREATED, savedCategory);
    }


    public GenericResponse updateCashbookEntry(long categoryPkId, CategoryDto categoryDto) {

        //validate if Category with PkId exists
        Categories existingCategory = (Categories) categoriesRepository.findByCategoryPkIdAndActiveFlag(categoryPkId, 1)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        //validate Category ownership to Update
        String emailId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        categoryValidationService.validateUserToUpdateCatrgory(existingCategory,emailId);

        // Validate duplicate category w.r.t Cashbook
        categoryValidationService.validateDuplicateCategory(categoryDto.getCategoryName(), existingCategory.getCashbook());

        //update category
        existingCategory.setCategoryName(categoryDto.getCategoryName());
        Categories updatedCategory=categoriesRepository.save(existingCategory);

        return new GenericResponse(CommonConstants.SUCCESS_STATUS, "Category " + CommonConstants.UPDATED, updatedCategory);

    }
}
