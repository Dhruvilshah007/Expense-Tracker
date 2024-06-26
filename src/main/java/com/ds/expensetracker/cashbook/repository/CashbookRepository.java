package com.ds.expensetracker.cashbook.repository;

import com.ds.expensetracker.authentication.model.User;
import com.ds.expensetracker.cashbook.model.Cashbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CashbookRepository extends JpaRepository<Cashbook,Long> {

    boolean existsByCashbookNameAndUser(String cashbookName, User user);

    List<Cashbook> findAllByUserAndActiveFlag(User user, int activeFlag);

    Optional<Object> findByCashbookPkIdAndActiveFlag(long cashbookPkId, int activeFlag);


    boolean existsByCashbookPkIdAndUser(long cashbookPkId, User user);
}
