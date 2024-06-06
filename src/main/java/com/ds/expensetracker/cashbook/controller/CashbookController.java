package com.ds.expensetracker.cashbook.controller;


import com.ds.expensetracker.cashbook.dto.CashbookDto;
import com.ds.expensetracker.cashbook.model.Cashbook;
import com.ds.expensetracker.cashbook.service.CashbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/cashbook")
@RestController
public class CashbookController {


    @Autowired
    private CashbookService cashbookService;


    @PostMapping("/createCashbook")
    public ResponseEntity<CashbookDto> createCashbook(@RequestBody Cashbook cashbook){
        String emailId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CashbookDto createdCashbook = cashbookService.createCashbookEntry(emailId, cashbook);
        return ResponseEntity.ok(createdCashbook);
    }

    @GetMapping("/getAllCashbook")
    public CashbookDto<List<Cashbook>> getAllCashbookOfUser(){
        String emailId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return cashbookService.getAllCashbookOfUser(emailId);
    }

    @PutMapping("/updateCashbook")
    public ResponseEntity<CashbookDto> updateCashbook(@RequestParam long cashbookPkId,@RequestBody Cashbook cashbook){
        CashbookDto createdCashbook = cashbookService.updateCashbookEntry(cashbookPkId, cashbook);
        return ResponseEntity.ok(createdCashbook);
    }


    @DeleteMapping("/deleteCashbook")
    public ResponseEntity<CashbookDto> deleteCashbook(@RequestParam long cashbookPkId){
        CashbookDto createdCashbook = cashbookService.deleteCashbookEntry(cashbookPkId);
        return ResponseEntity.ok(createdCashbook);
    }


}
