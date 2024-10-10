package com.ds.expensetracker.authentication.repository;


import com.ds.expensetracker.authentication.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken,Long> {

    @Query("SELECT prt FROM PasswordResetToken prt WHERE prt.token = :token AND prt.activeFlag = 1 AND prt.expiryDate > CURRENT_TIMESTAMP")
    PasswordResetToken findActiveToken(@Param("token") String token);

}
