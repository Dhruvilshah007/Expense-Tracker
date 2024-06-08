package com.ds.expensetracker.cashbook.service;


import com.ds.expensetracker.authentication.model.User;
import com.ds.expensetracker.authentication.repository.UserRepository;
import com.ds.expensetracker.authentication.util.UserUtility;
import com.ds.expensetracker.cashbook.model.Cashbook;
import com.ds.expensetracker.cashbook.repository.CashbookRepository;
import com.ds.expensetracker.common.constants.CommonConstants;
import com.ds.expensetracker.common.response.GenericResponse;
import com.ds.expensetracker.exception.commonException.ApplicationException;
import org.springframework.http.HttpStatusCode;
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

    public GenericResponse createCashbook(Cashbook cashbook, String clientIpAddress) {

        User user = UserUtility.getCurrentUser();

        //validate Duplicate cashbook Name for given user
        cashbookValidationService.validateDuplicateCashbookName(cashbook.getCashbookName(), user);

        cashbook.setUser(user);
        cashbook.setCreatedByIpaddress(clientIpAddress);
        Cashbook savedCashbook = cashbookRepository.save(cashbook);

        return new GenericResponse(CommonConstants.SUCCESS_STATUS, "Cashbook " + CommonConstants.CREATED, cashbook);
    }

    public GenericResponse<List<Cashbook>> getAllCashbookOfUser() {
        User user = UserUtility.getCurrentUser();
        List<Cashbook> cashbookList = cashbookRepository.findAllByUserAndActiveFlag(user, CommonConstants.ACTIVE_FLAG);

        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setStatus("Success");
        if (cashbookList.isEmpty()) {
            genericResponse.setMessage("No Cashbook found");
        } else {
            genericResponse.setData(cashbookList);
        }
        return genericResponse;
    }


    public GenericResponse updateCashbook(long cashbookPkId, Cashbook cashbook) {
        cashbookValidationService.validateCashbookOwnership(cashbookPkId, UserUtility.getCurrentUserEmail());

        Cashbook existingCashbook = (Cashbook) cashbookRepository.findByCashbookPkIdAndActiveFlag(cashbookPkId, 1)
                .orElseThrow(() -> new ApplicationException(HttpStatusCode.valueOf(404), "Not Found", "Cashbook not found"));

        existingCashbook.setCashbookName(cashbook.getCashbookName());
        Cashbook updatedCashbook = cashbookRepository.save(existingCashbook);

        GenericResponse responseDto = new GenericResponse();
        responseDto.setStatus("Success");
        responseDto.setMessage("Cashbook Updated Successfully");
        responseDto.setData(updatedCashbook);
        return responseDto;
    }

    public GenericResponse deleteCashbook(long cashbookPkId) {
        cashbookValidationService.validateCashbookOwnership(cashbookPkId,  UserUtility.getCurrentUserEmail());

        Cashbook existingCashbook = cashbookRepository.findById(cashbookPkId)
                .orElseThrow(() -> new ApplicationException(HttpStatusCode.valueOf(404), "Not Found", "Cashbook not found"));

        existingCashbook.setActiveFlag(0);
        cashbookRepository.save(existingCashbook);

        GenericResponse responseDto = new GenericResponse();
        responseDto.setStatus("Success");
        responseDto.setMessage("Cashbook Deleted Successfully");
        return responseDto;
    }
}
