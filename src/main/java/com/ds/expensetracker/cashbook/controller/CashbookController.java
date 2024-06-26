package com.ds.expensetracker.cashbook.controller;


import com.ds.expensetracker.cashbook.dto.CashbookDto;
import com.ds.expensetracker.cashbook.service.CashbookService;
import com.ds.expensetracker.common.response.GenericResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/cashbook")
@RestController
@RequiredArgsConstructor
public class CashbookController {

    private final CashbookService cashbookService;

    @PostMapping
    public ResponseEntity<?> createCashbook(@Valid @RequestBody CashbookDto cashbookDto, HttpServletRequest request) {
        return ResponseEntity.ok(cashbookService.createCashbook(cashbookDto, request.getRemoteAddr()));
    }

    @GetMapping("/{cashbookPkId}")
    public ResponseEntity<?> getCashbookByCashbookPkId(
            @PathVariable Long cashbookPkId
    ) {
        return ResponseEntity.ok(cashbookService.getCashbookByCashbookPkId(cashbookPkId));
    }

    @PutMapping("/{cashbookPkId}")
    public ResponseEntity<GenericResponse> updateCashbook(@PathVariable long cashbookPkId, @Valid @RequestBody CashbookDto cashbookDto, HttpServletRequest request) {
        return ResponseEntity.ok(cashbookService.updateCashbook(cashbookPkId, cashbookDto));
    }

    @DeleteMapping("/{cashbookPkId}")
    public ResponseEntity<GenericResponse> deleteCashbook(@PathVariable long cashbookPkId, HttpServletRequest request) {
        return ResponseEntity.ok(cashbookService.deleteCashbook(cashbookPkId));
    }

    @GetMapping("/getAllCashbook")
    public GenericResponse<?> getAllCashbookOfUser() {
        return cashbookService.getAllCashbookOfUser();
    }

}
