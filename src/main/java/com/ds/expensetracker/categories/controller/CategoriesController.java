package com.ds.expensetracker.categories.controller;


import com.ds.expensetracker.categories.dto.CategoryDto;
import com.ds.expensetracker.categories.service.CategoriesService;
import com.ds.expensetracker.common.response.GenericResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/cashbook/categories")
@RestController
@RequiredArgsConstructor
public class CategoriesController {

    private final CategoriesService categoriesService;


    @PostMapping()
    public ResponseEntity<?> createCashbook(@Valid @RequestBody CategoryDto categoryDto, HttpServletRequest request) {
        return ResponseEntity.ok(categoriesService.createCategory(categoryDto, request.getRemoteAddr()));
    }

    @GetMapping("/{categoryPkId}")
    public ResponseEntity<?> getCategory(@PathVariable Long categoryPkId) {
        return ResponseEntity.ok(categoriesService.getCategory(categoryPkId));
    }

    @PutMapping("/{categoryPkId}")
    public ResponseEntity<?> updateCategory(@PathVariable Long categoryPkId, @RequestBody CategoryDto categoryDto, HttpServletRequest request) {
        return ResponseEntity.ok(categoriesService.updateCategory(categoryPkId, categoryDto, request.getRemoteAddr()));
    }

    @DeleteMapping("/{categoryPkId}")
    public ResponseEntity<GenericResponse> deleteCategory(@PathVariable Long categoryPkId, HttpServletRequest request) {
        return ResponseEntity.ok(categoriesService.deleteCategory(categoryPkId, request.getRemoteAddr()));
    }

}
