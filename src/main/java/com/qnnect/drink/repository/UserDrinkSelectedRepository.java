package com.qnnect.drink.repository;

import com.qnnect.drink.domain.UserDrinkSelected;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDrinkSelectedRepository extends
        JpaRepository<UserDrinkSelected, Long> {
    List<UserDrinkSelected> findAllByCafeUser_Id(@Param(value = "cafeUserId") long cafeUserId);
}
