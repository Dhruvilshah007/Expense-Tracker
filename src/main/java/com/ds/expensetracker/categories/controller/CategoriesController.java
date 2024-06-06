package com.ds.expensetracker.categories.controller;


import com.ds.expensetracker.categories.dto.CategoryDto;
import com.ds.expensetracker.categories.service.CategoriesService;
import com.ds.expensetracker.common.response.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/categories")
@RestController
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;


    @PostMapping("/createCategory")
    public ResponseEntity<GenericResponse> createCashbook(@RequestBody CategoryDto categoryDto){
        GenericResponse genericResponse = categoriesService.createCategory(categoryDto);
        return ResponseEntity.ok(genericResponse);
    }

    @PutMapping("/updateCategory")
    public ResponseEntity<GenericResponse> updateCategory(@RequestParam long categoryPkId, @RequestBody CategoryDto categoryDto){
        GenericResponse genericResponse = categoriesService.updateCashbookEntry(categoryPkId, categoryDto);
        return ResponseEntity.ok(genericResponse);
    }



}
