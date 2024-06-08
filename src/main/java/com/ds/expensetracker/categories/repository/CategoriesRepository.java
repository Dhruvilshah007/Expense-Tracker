package com.ds.expensetracker.categories.repository;

import com.ds.expensetracker.cashbook.model.Cashbook;
import com.ds.expensetracker.categories.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CategoriesRepository extends JpaRepository<Categories,Long> {

    boolean existsByCategoryNameAndCashbook(String categoryName, Cashbook cashbook);

    Optional<Object> findByCategoryPkIdAndActiveFlag(long categoryPkId, int activeFlag);

}
