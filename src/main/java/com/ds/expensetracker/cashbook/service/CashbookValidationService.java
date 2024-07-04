package com.ds.expensetracker.cashbook.service;

import com.ds.expensetracker.authentication.model.User;
import com.ds.expensetracker.authentication.repository.UserRepository;
import com.ds.expensetracker.cashbook.model.Cashbook;
import com.ds.expensetracker.cashbook.repository.CashbookRepository;
import com.ds.expensetracker.common.constants.CommonConstants;
import com.ds.expensetracker.exception.commonException.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CashbookValidationService {

    private final CashbookRepository cashbookRepository;
    private final UserRepository userRepository;


    //Not used in cashbook Kept for reference purpose
    public Cashbook validateCashbookExists(Long cashbookPkId) {
        return (Cashbook) cashbookRepository.findByCashbookPkIdAndActiveFlag(cashbookPkId, CommonConstants.ACTIVE_FLAG)
                .orElseThrow(() -> new ApplicationException(
                        HttpStatusCode.valueOf(404),
                        "Invalid Cashbook Id",
                        "The provided Cashbook Id does not exist or is invalid."
                ));
    }

    public Cashbook validateCashbookOwnership(long cashbookPkId, String emailId) {
        Cashbook cashbook = (Cashbook) cashbookRepository.findByCashbookPkIdAndActiveFlag(cashbookPkId, CommonConstants.ACTIVE_FLAG)
                .orElseThrow(() -> new ApplicationException(
                        HttpStatusCode.valueOf(404),
                        "Invalid Cashbook Id",
                        "The provided Cashbook Id does not exist or is invalid."
                ));


        User user = cashbook.getUser();

        if (!user.getEmailId().equals(emailId)) {
            throw new ApplicationException(
                    HttpStatusCode.valueOf(404),
                    "Unauthorized access",
                    "You are not authorized to access cashbook"
            );
        }

        return cashbook;
    }

    public void validateDuplicateCashbookName(String cashbookName, User user) {
        if (cashbookRepository.existsByCashbookNameAndUser(cashbookName, user)) {
            throw new ApplicationException(
                    HttpStatusCode.valueOf(409),
                    "Duplicate Cashbook Name",
                    "Cashbook with Cashbook Name - " + cashbookName + " already exists"
            );
        }
    }


}
