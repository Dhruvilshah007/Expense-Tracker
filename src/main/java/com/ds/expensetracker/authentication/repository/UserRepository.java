package com.ds.expensetracker.authentication.repository;

import com.ds.expensetracker.authentication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;



@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmailId(String emailId);


}
