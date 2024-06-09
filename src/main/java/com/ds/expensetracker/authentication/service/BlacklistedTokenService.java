package com.ds.expensetracker.authentication.service;


import com.ds.expensetracker.authentication.model.BlacklistedToken;
import com.ds.expensetracker.authentication.repository.BlacklistedTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class BlacklistedTokenService {
    @Autowired
    BlacklistedTokenRepository blacklistedTokenRepository;

    public void addToBlacklist(String token, LocalDateTime expirationTime, String clientIpAddress) {

        BlacklistedToken blacklistedToken=new BlacklistedToken();
        blacklistedToken.setToken(token);
        blacklistedToken.setExpiryDate(expirationTime);
        blacklistedToken.setCreatedByIpaddress(clientIpAddress);

        blacklistedTokenRepository.save(blacklistedToken);
    }


    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokenRepository.existsByTokenAndExpiryDateAfter(token, LocalDateTime.now());
    }


    //Scheduled Job to delete Expired tokens4

//    300000->3 mins, 86400000->24Hrs in Milliseocnds

    @Transactional //to ensure delete operation is executed within transaction and EntityManager is available
    @Scheduled(fixedRate = 86400000)
    public void deleteExpiredTokensFromBlacklist() {
        LocalDateTime now = LocalDateTime.now();
        blacklistedTokenRepository.deleteByExpiryDateBefore(now);
    }

}
