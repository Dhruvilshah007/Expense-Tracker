package com.ds.expensetracker.cashbook.service;

import com.ds.expensetracker.authentication.model.User;
import com.ds.expensetracker.authentication.repository.UserRepository;
import com.ds.expensetracker.cashbook.model.Cashbook;
import com.ds.expensetracker.cashbook.repository.CashbookRepository;
import com.ds.expensetracker.exception.cashbook.DuplicateCashbookException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CashbookValidationService {


    private final CashbookRepository cashbookRepository;
    private final UserRepository userRepository;

    public CashbookValidationService(CashbookRepository cashbookRepository, UserRepository userRepository) {
        this.cashbookRepository = cashbookRepository;
        this.userRepository = userRepository;
    }

    public Cashbook validateCashbookExists(Long cashbookPkId) {
        return cashbookRepository.findById(cashbookPkId)
                .orElseThrow(() -> new RuntimeException("Invalid Cashbook Id"));
    }

    public void validateUserHasRelationWithCashbook(Long cashbookPkId, User user) {
        if (!cashbookRepository.existsByCashbookPkIdAndUser(cashbookPkId, user)) {
            throw new RuntimeException("Invalid CashbookPkId for User");
        }
    }

    public void validateCashbookOwnership(long cashbookPkId, String emailId) {
        Cashbook cashbook = cashbookRepository.findById(cashbookPkId)
                .orElseThrow(() -> new RuntimeException("Cashbook not found"));

        User user = userRepository.findByEmailId(emailId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!cashbook.getUser().getEmailId().equals(emailId)) {
            throw new RuntimeException("Unauthorized access to update Cashbook");
        }
    }

    public void validateDuplicateCashbookName(String cashbookName, User user) {
        if (cashbookRepository.existsByCashbookNameAndUser(cashbookName, user)) {
            throw new DuplicateCashbookException("Cashbook with name '" + cashbookName + "' already exists");
        }
    }
}
