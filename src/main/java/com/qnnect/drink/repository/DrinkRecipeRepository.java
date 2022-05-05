package com.qnnect.drink.repository;

import com.qnnect.drink.domain.DrinkRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DrinkRecipeRepository extends JpaRepository<DrinkRecipe, Long> {

    List<DrinkRecipe> findAllByDrink_Id(@Param(value = "drinkId")long drinkId);
}
