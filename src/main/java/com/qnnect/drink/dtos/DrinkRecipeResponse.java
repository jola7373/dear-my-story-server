package com.qnnect.drink.dtos;

import com.qnnect.drink.domain.DrinkRecipe;
import com.qnnect.ingredients.domain.EIngredientType;
import com.qnnect.ingredients.domain.UserIngredient;
import com.qnnect.ingredients.dto.MyIngredientResponse;
import io.swagger.annotations.ApiModelProperty;
import kotlin.BuilderInference;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class DrinkRecipeResponse {
    @ApiModelProperty(value = "재료 아이디", example = "1")
    private Long ingredientId;

    @ApiModelProperty(value = "재료명", example = "녹차가루")
    private String name;

    @ApiModelProperty(value = "재료 종류", example = "토핑")
    private EIngredientType ingredientType;

    @ApiModelProperty(value = "갯수", example = "30")
    private long count;


    public static DrinkRecipeResponse from(DrinkRecipe drinkRecipe) {

        return DrinkRecipeResponse.builder()
                .ingredientId(drinkRecipe.getIngredient().getId())
                .name(drinkRecipe.getIngredient().getName())
                .ingredientType(drinkRecipe.getIngredient().getIngredientType())
                .count(drinkRecipe.getNumber())
                .build();
    }

    public static List<DrinkRecipeResponse> listfrom(List<DrinkRecipe> drinkRecipes){
        return drinkRecipes.stream()
                .map(DrinkRecipeResponse::from)
                .collect(Collectors.toList());
    }
}
