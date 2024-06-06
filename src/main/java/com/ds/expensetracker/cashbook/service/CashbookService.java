package com.ds.expensetracker.cashbook.service;


import com.ds.expensetracker.authentication.model.User;
import com.ds.expensetracker.authentication.repository.UserRepository;
import com.ds.expensetracker.cashbook.dto.CashbookDto;
import com.ds.expensetracker.cashbook.model.Cashbook;
import com.ds.expensetracker.cashbook.repository.CashbookRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CashbookService {


    private final CashbookRepository cashbookRepository;
    private final UserRepository userRepository;
    private final CashbookValidationService cashbookValidationService;

    public CashbookService(CashbookRepository cashbookRepository, UserRepository userRepository, CashbookValidationService cashbookValidationService) {
        this.cashbookRepository = cashbookRepository;
        this.userRepository = userRepository;
        this.cashbookValidationService = cashbookValidationService;
    }

    public CashbookDto createCashbookEntry(String emailId, Cashbook cashbook) {
        User user = getUserByEmailId(emailId);

        cashbookValidationService.validateDuplicateCashbookName(cashbook.getCashbookName(), user);

        cashbook.setUser(user);
        Cashbook savedCashbook = cashbookRepository.save(cashbook);

        CashbookDto cashbookDto = new CashbookDto();
        cashbookDto.setStatus("Success");
        cashbookDto.setMessage("Cashbook Created Successfully");
        return cashbookDto;
    }

    public CashbookDto<List<Cashbook>> getAllCashbookOfUser(String emailId) {
        User user = getUserByEmailId(emailId);
        List<Cashbook> cashbookList = cashbookRepository.findAllByUserAndActiveFlag(user, 1);

        CashbookDto cashbookDto = new CashbookDto();
        cashbookDto.setStatus("Success");

        if (cashbookList.isEmpty()) {
            cashbookDto.setMessage("No Cashbook found");
        } else {
            cashbookDto.setData(cashbookList);
        }

        return cashbookDto;
    }


    public CashbookDto updateCashbookEntry(long cashbookPkId, Cashbook cashbook) {
        cashbookValidationService.validateCashbookOwnership(cashbookPkId, getCurrentUserEmailId());

        Cashbook existingCashbook = (Cashbook) cashbookRepository.findByCashbookPkIdAndActiveFlag(cashbookPkId, 1)
                .orElseThrow(() -> new RuntimeException("Cashbook not found"));

        existingCashbook.setCashbookName(cashbook.getCashbookName());
        Cashbook updatedCashbook = cashbookRepository.save(existingCashbook);

        CashbookDto responseDto = new CashbookDto();
        responseDto.setStatus("Success");
        responseDto.setMessage("Cashbook Updated Successfully");
        responseDto.setData(updatedCashbook);
        return responseDto;
    }

    public CashbookDto deleteCashbookEntry(long cashbookPkId) {
        cashbookValidationService.validateCashbookOwnership(cashbookPkId, getCurrentUserEmailId());

        Cashbook existingCashbook = cashbookRepository.findById(cashbookPkId)
                .orElseThrow(() -> new RuntimeException("Cashbook not found"));

        existingCashbook.setActiveFlag(0);
        cashbookRepository.save(existingCashbook);

        CashbookDto responseDto = new CashbookDto();
        responseDto.setStatus("Success");
        responseDto.setMessage("Cashbook Deleted Successfully");
        return responseDto;
    }

    private User getUserByEmailId(String emailId) {
        return userRepository.findByEmailId(emailId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private String getCurrentUserEmailId() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
