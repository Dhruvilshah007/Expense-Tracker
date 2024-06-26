package com.ds.expensetracker.cashbook.service;


import com.ds.expensetracker.authentication.model.User;
import com.ds.expensetracker.authentication.repository.UserRepository;
import com.ds.expensetracker.authentication.util.UserUtility;
import com.ds.expensetracker.cashbook.dto.CashbookDto;
import com.ds.expensetracker.cashbook.model.Cashbook;
import com.ds.expensetracker.cashbook.repository.CashbookRepository;
import com.ds.expensetracker.common.constants.CommonConstants;
import com.ds.expensetracker.common.response.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CashbookService {
    private final CashbookRepository cashbookRepository;
    private final UserRepository userRepository;
    private final CashbookValidationService cashbookValidationService;

    public GenericResponse createCashbook(CashbookDto cashbookDto, String clientIpAddress) {

        User user = UserUtility.getCurrentUser();

        //validate Duplicate cashbook Name for given user
        cashbookValidationService.validateDuplicateCashbookName(cashbookDto.getCashbookName(), user);

        Cashbook cashbook = new Cashbook();
        BeanUtils.copyProperties(cashbookDto, cashbook);
        cashbook.setUser(user);
        cashbook.setCreatedByIpaddress(clientIpAddress);
        Cashbook savedCashbook = cashbookRepository.save(cashbook);

        BeanUtils.copyProperties(savedCashbook, cashbookDto);

        return GenericResponse.builder().status(CommonConstants.SUCCESS_STATUS).message(CommonConstants.CASHBOOK_CREATED).data(cashbookDto).build();
    }

    public GenericResponse<List<Cashbook>> getAllCashbookOfUser() {
        User user = UserUtility.getCurrentUser();
        List<Cashbook> cashbookList = cashbookRepository.findAllByUserAndActiveFlag(user, CommonConstants.ACTIVE_FLAG);

        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setStatus(CommonConstants.SUCCESS_STATUS);
        if (cashbookList.isEmpty()) {
            genericResponse.setMessage(CommonConstants.CASHBOOK_NOT_FOUND);
        } else {
            genericResponse.setMessage("All Cashbook found");
            genericResponse.setData(cashbookList);
        }
        return genericResponse;
    }


    public GenericResponse updateCashbook(long cashbookPkId, CashbookDto cashbookDto) {
        Cashbook existingCashbook =cashbookValidationService.validateCashbookOwnership(cashbookPkId, UserUtility.getCurrentUserEmail());

        BeanUtils.copyProperties(cashbookDto, existingCashbook,"cashbookPkId");
        existingCashbook.setCashbookName(cashbookDto.getCashbookName());
        Cashbook updatedCashbook = cashbookRepository.save(existingCashbook);

        BeanUtils.copyProperties(updatedCashbook, cashbookDto);

        return GenericResponse.builder().status(CommonConstants.SUCCESS_STATUS).message(CommonConstants.CASHBOOK_UPDATED).data(cashbookDto).build();
    }

    public GenericResponse deleteCashbook(long cashbookPkId) {
        Cashbook existingCashbook=cashbookValidationService.validateCashbookOwnership(cashbookPkId, UserUtility.getCurrentUserEmail());

        existingCashbook.setActiveFlag(0);
        cashbookRepository.save(existingCashbook);

        return GenericResponse.builder().status(CommonConstants.SUCCESS_STATUS).message(CommonConstants.CASHBOOK_DELETED).build();
    }

    public CashbookDto getCashbookByCashbookPkId(Long cashbookPkId) {

        Cashbook existingCashbook=cashbookValidationService.validateCashbookOwnership(cashbookPkId, UserUtility.getCurrentUserEmail());

        return CashbookDto.builder().cashbookName(existingCashbook.getCashbookName()).cashbookPkId(existingCashbook.getCashbookPkId()).build();
    }
}
