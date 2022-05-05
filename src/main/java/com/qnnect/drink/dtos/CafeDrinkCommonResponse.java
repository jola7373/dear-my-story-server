package com.qnnect.drink.dtos;

import com.qnnect.drink.domain.DrinkIngredientsFilled;
import com.qnnect.drink.domain.DrinkRecipe;
import com.qnnect.drink.domain.UserDrinkSelected;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CafeDrinkCommonResponse {
    private Long userDrinkSelectedId;
    private String userDrinkName;
    private List<DrinkIngredientsFilledResponse> currentDrinkIngredientsFilled;
    private int ice;
    private int iceFilled;
    private int base;
    private int baseFilled;
    private int main;
    private int mainFilled;
    private int topping;
    private int toppingFilled;

    @Builder
    public CafeDrinkCommonResponse(UserDrinkSelected drinkSelected,
                                   List<DrinkRecipe> drinkRecipes, int size) {
        if (drinkSelected != null) {
            List<DrinkIngredientsFilled> drinkIngredientsFilled = drinkSelected.getDrinkIngredientsFilled();
            this.userDrinkSelectedId = drinkSelected.getId();
            this.currentDrinkIngredientsFilled =
                    DrinkIngredientsFilledResponse.listFrom(drinkIngredientsFilled);
            this.userDrinkName = drinkSelected.getDrink().getName();
            this.ice = drinkRecipes.get(0).getNumber();
            this.base = drinkRecipes.get(1).getNumber();
            this.main = drinkRecipes.get(2).getNumber();
            this.topping = drinkRecipes.get(3).getNumber();
            size = drinkIngredientsFilled.size();

            if (size > ice) {
                iceFilled = ice;
                size -= ice;
            } else {
                this.iceFilled = size;
                size = 0;
            }

            if (size > base) {
                baseFilled = base;
                size -= base;
            } else {
                this.baseFilled = size;
                size = 0;
            }

            if (size > main) {
                mainFilled = main;
                size -= main;
            } else {
                this.mainFilled = size;
                size = 0;
            }

            if (size > topping) {
                toppingFilled = topping;
                size -= topping;
            } else {
                this.toppingFilled = size;
                size = 0;
            }
        }

    }
}
