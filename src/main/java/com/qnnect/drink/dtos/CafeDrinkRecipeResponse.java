package com.qnnect.drink.dtos;

import com.qnnect.cafe.domain.CafeUser;
import com.qnnect.drink.domain.DrinkRecipe;
import com.qnnect.ingredients.dto.MyIngredientResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CafeDrinkRecipeResponse {
    private CafeDrinkCommonResponse currentDrinkInfo;
    private List<DrinkRecipeResponse> drinkRecipeResponses;


    public CafeDrinkRecipeResponse(CafeUser drinkOwner, List<DrinkRecipe> drinkRecipes,
                                       int size){
        this.currentDrinkInfo = new CafeDrinkCommonResponse(drinkOwner.getUserDrinkSelected().get(0), drinkRecipes, size);
        this.drinkRecipeResponses = DrinkRecipeResponse.listfrom(drinkRecipes);
    }
}
