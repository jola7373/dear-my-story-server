package com.qnnect.ingredients.dto;

import com.qnnect.ingredients.domain.EIngredientType;
import com.qnnect.ingredients.domain.UserIngredient;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@ApiModel(value="내 재료 가져오기")
@Getter
@Builder
public class MyIngredientResponse {
    @ApiModelProperty(value = "재료 아이디", example = "1")
    private Long ingredientId;

    @ApiModelProperty(value = "재료명", example = "녹차가루")
    private String name;

    @ApiModelProperty(value = "재료 종류", example = "토핑")
    private EIngredientType ingredientType;

    @ApiModelProperty(value = "갯수", example = "30")
    private long count;


    public static MyIngredientResponse from(UserIngredient userIngredient, long count) {

        return MyIngredientResponse.builder()
                .ingredientId(userIngredient.getIngredient().getId())
                .name(userIngredient.getIngredient().getName())
                .ingredientType(userIngredient.getIngredient().getIngredientType())
                .count(count)
                .build();
    }

    public static List<MyIngredientResponse> listfrom(List<Object[]> userIngredients){
        return userIngredients.stream()
                .map(userIngredient -> MyIngredientResponse
                .from(((UserIngredient) userIngredient[0]),
                        ((long) userIngredient[1]))).collect(Collectors.toList());
    }


}
