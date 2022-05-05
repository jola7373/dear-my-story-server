package com.qnnect.drink.repository;

import com.qnnect.drink.domain.DrinkIngredientsFilled;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface DrinkIngredientsFilledRepository extends JpaRepository<DrinkIngredientsFilled, Long> {
    long countByUserDrinkSelected_Id(@Param(value = "userDrinkSelectedId")long userDrinkSelectedId);
}