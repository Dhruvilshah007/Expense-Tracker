package com.ds.expensetracker.categories.controller;


import com.ds.expensetracker.categories.dto.CategoryDto;
import com.ds.expensetracker.categories.model.Categories;
import com.ds.expensetracker.categories.service.CategoriesService;
import com.ds.expensetracker.common.response.GenericResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/categories")
@RestController
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;


    @PostMapping("/createCategory")
    public ResponseEntity<GenericResponse> createCashbook(@RequestBody CategoryDto categoryDto, HttpServletRequest request) {
        GenericResponse genericResponse = categoriesService.createCategory(categoryDto, request.getRemoteAddr());
        return ResponseEntity.ok(genericResponse);
    }

    @GetMapping("/getCategory")
    public ResponseEntity<Categories> getCategory(@RequestParam long categoryPkId) {
        Categories categories = categoriesService.getCategory(categoryPkId);
        return ResponseEntity.ok(categories);
    }

    @PutMapping("/updateCategory")
    public ResponseEntity<GenericResponse> updateCategory(@RequestParam long categoryPkId, @RequestBody CategoryDto categoryDto, HttpServletRequest request) {
        GenericResponse genericResponse = categoriesService.updateCategory(categoryPkId, categoryDto, request.getRemoteAddr());
        return ResponseEntity.ok(genericResponse);
    }

    @DeleteMapping("/deleteCategory")
    public ResponseEntity<GenericResponse> deleteCategory(@RequestParam long categoryPkId, HttpServletRequest request) {
        GenericResponse genericResponse = categoriesService.deleteCategory(categoryPkId, request.getRemoteAddr());
        return ResponseEntity.ok(genericResponse);
    }

}
