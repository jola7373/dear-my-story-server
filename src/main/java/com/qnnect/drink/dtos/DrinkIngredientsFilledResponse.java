package com.qnnect.drink.dtos;

import com.qnnect.drink.domain.Drink;
import com.qnnect.drink.domain.DrinkIngredientsFilled;
import com.qnnect.drink.domain.UserDrinkSelected;
import com.qnnect.ingredients.domain.Ingredient;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@ApiModel
public class DrinkIngredientsFilledResponse {
    private String ingredientName;


    public static DrinkIngredientsFilledResponse from(DrinkIngredientsFilled drinkIngredientsFilled) {
        return DrinkIngredientsFilledResponse.builder()
                .ingredientName(drinkIngredientsFilled.getIngredient().getName())
                .build();
    }

    public static List<DrinkIngredientsFilledResponse> listFrom(List<DrinkIngredientsFilled>
                                                       drinkIngredientsFilledList) {
        return drinkIngredientsFilledList.stream()
                .map(DrinkIngredientsFilledResponse::from)
                .collect(Collectors.toList());
    }
}
