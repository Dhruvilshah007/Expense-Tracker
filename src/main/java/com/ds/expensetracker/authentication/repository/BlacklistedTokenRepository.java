package com.ds.expensetracker.authentication.repository;

import com.ds.expensetracker.authentication.model.BlacklistedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Repository
public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken, Long> {

    boolean existsByTokenAndExpiryDateAfter(String token, LocalDateTime currentTime);


    @Transactional
    void deleteByExpiryDateBefore(LocalDateTime expiryDate);
}