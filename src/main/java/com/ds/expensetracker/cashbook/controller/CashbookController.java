package com.ds.expensetracker.cashbook.controller;


import com.ds.expensetracker.cashbook.model.Cashbook;
import com.ds.expensetracker.cashbook.service.CashbookService;
import com.ds.expensetracker.common.response.GenericResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/cashbook")
@RestController
public class CashbookController {
    @Autowired
    private CashbookService cashbookService;


    @PostMapping("/createCashbook")
    public ResponseEntity<GenericResponse> createCashbook(@RequestBody Cashbook cashbook, HttpServletRequest request) {
        GenericResponse genericResponse = cashbookService.createCashbook(cashbook, request.getRemoteAddr());
        return ResponseEntity.ok(genericResponse);
    }

    @GetMapping("/getAllCashbook")
    public GenericResponse<List<Cashbook>> getAllCashbookOfUser() {
        return cashbookService.getAllCashbookOfUser();
    }

    @PutMapping("/updateCashbook")
    public ResponseEntity<GenericResponse> updateCashbook(@RequestParam long cashbookPkId, @RequestBody Cashbook cashbook, HttpServletRequest request) {
        GenericResponse createdCashbook = cashbookService.updateCashbook(cashbookPkId, cashbook);
        return ResponseEntity.ok(createdCashbook);
    }


    @DeleteMapping("/deleteCashbook")
    public ResponseEntity<GenericResponse> deleteCashbook(@RequestParam long cashbookPkId, HttpServletRequest request) {
        GenericResponse createdCashbook = cashbookService.deleteCashbook(cashbookPkId);
        return ResponseEntity.ok(createdCashbook);
    }


}
