package com.qnnect.drink.dtos;

import com.qnnect.cafe.domain.CafeUser;
import com.qnnect.drink.domain.DrinkRecipe;
import com.qnnect.ingredients.dto.MyIngredientResponse;
import lombok.Getter;

import java.util.List;

@Getter
public class CafeDrinkIngredientResponse {
    private CafeDrinkCommonResponse currentDrinkInfo;
    private List<MyIngredientResponse> myIngredient;

    public CafeDrinkIngredientResponse(CafeUser drinkOwner, List<DrinkRecipe> drinkRecipes,
                                       int size, List<Object[]> ingredients ){
        this.currentDrinkInfo = new CafeDrinkCommonResponse(drinkOwner.getUserDrinkSelected().get(0), drinkRecipes, size);
        this.myIngredient = MyIngredientResponse.listfrom(ingredients);
    }
}
