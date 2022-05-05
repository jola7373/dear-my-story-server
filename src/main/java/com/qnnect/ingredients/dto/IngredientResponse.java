package com.qnnect.ingredients.dto;

import com.qnnect.drink.domain.DrinkIngredientsFilled;
import com.qnnect.drink.dtos.DrinkIngredientsFilledResponse;
import com.qnnect.ingredients.domain.EIngredientType;
import com.qnnect.ingredients.domain.Ingredient;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;


@ApiModel(value="재료 가져오기")
@Getter
@Builder
public class IngredientResponse {

    @ApiModelProperty(value = "재료 아이디", example = "1")
    private Long id;

    @ApiModelProperty(value = "재료명", example = "녹차가루")
    private String name;

    @ApiModelProperty(value = "재료 종류", example = "토핑")
    private EIngredientType ingredientType;

    @ApiModelProperty(value = "가격", example = "30")
    private int point;


    public static IngredientResponse from(Ingredient ingredient) {
        return IngredientResponse.builder()
                .name(ingredient.getName())
                .id(ingredient.getId())
                .point(ingredient.getPoint())
                .ingredientType(ingredient.getIngredientType())
                .build();
    }

    public static List<IngredientResponse> listFrom(List<Ingredient> ingredientList) {
        return ingredientList.stream()
                .map(IngredientResponse::from)
                .collect(Collectors.toList());
    }
}
