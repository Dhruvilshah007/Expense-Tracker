package com.ds.expensetracker.cashbook.service;


import com.ds.expensetracker.authentication.model.User;
import com.ds.expensetracker.authentication.repository.UserRepository;
import com.ds.expensetracker.cashbook.dto.CashbookDto;
import com.ds.expensetracker.cashbook.model.Cashbook;
import com.ds.expensetracker.cashbook.repository.CashbookRepository;
import com.ds.expensetracker.exception.cashbook.DuplicateCashbookException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CashbookService {

    @Autowired
    private CashbookRepository cashbookRepository;

    @Autowired
    private UserRepository userRepository;

    public CashbookDto createCashbookEntry(String emailId, Cashbook cashbook) {

    User user=userRepository.findByEmailId(emailId).orElseThrow(()->new UsernameNotFoundException("User not found"));
    cashbook.setUser(user);

      if (cashbookRepository.existsByCashbookNameAndUser(cashbook.getCashbookName(),user)) {
          throw new DuplicateCashbookException("Cashbook with name '" + cashbook.getCashbookName() + "' already exists");
      }

      Cashbook savedCashbook=cashbookRepository.save(cashbook);

      CashbookDto cashbookDto=new CashbookDto();
      cashbookDto.setStatus("Success");
      cashbookDto.setMessage("Cashbook Created Successfully");

      return cashbookDto;

    }

    public CashbookDto<List<Cashbook>> getAllCashbookOfUser(String emailId) {

        User user=userRepository.findByEmailId(emailId).orElseThrow(()->new UsernameNotFoundException("User not found"));
        List<Cashbook> cashbookList=cashbookRepository.findAllByUserAndActiveFlag(user,1);
        CashbookDto cashbookDto=new CashbookDto();

        cashbookDto.setStatus("Success");
        if(cashbookList.isEmpty()){
            cashbookDto.setMessage("No Cashbook found");
        }else{
            cashbookDto.setData(cashbookList);
        }

        return cashbookDto;
    }


    public CashbookDto updateCashbookEntry(long cashbookPkId, Cashbook cashbook) {
        Cashbook existingCashbook = (Cashbook) cashbookRepository.findByCashbookPkIdAndActiveFlag(cashbookPkId, 1)
                .orElseThrow(() -> new RuntimeException("Cashbook not found"));

        // Check if the user making the request is the owner of the cashbook
        String emailId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!existingCashbook.getUser().getEmailId().equals(emailId)) {
            throw new RuntimeException("Unauthorized access to update Cashbook");
        }

        existingCashbook.setCashbookName(cashbook.getCashbookName());
        Cashbook updatedCashbook = cashbookRepository.save(existingCashbook);

        CashbookDto responseDto = new CashbookDto();
        responseDto.setStatus("Success");
        responseDto.setMessage("Cashbook Updated Successfully");
        responseDto.setData(updatedCashbook);
        return responseDto;
    }

    public CashbookDto deleteCashbookEntry(long cashbookPkId) {

        Cashbook existingCashbook = cashbookRepository.findById(cashbookPkId)
                .orElseThrow(() -> new RuntimeException("Cashbook not found"));

        // Check if the user making the request is the owner of the cashbook
        String emailId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!existingCashbook.getUser().getEmailId().equals(emailId)) {
            throw new RuntimeException("Unauthorized access to update Cashbook");
        }

        existingCashbook.setActiveFlag(0);

        CashbookDto responseDto = new CashbookDto();
        responseDto.setStatus("Success");
        responseDto.setMessage("Cashbook Deleted Successfully");
        return responseDto;
    }
}
