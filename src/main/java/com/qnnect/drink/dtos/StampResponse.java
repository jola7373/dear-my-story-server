package com.qnnect.drink.dtos;

import com.qnnect.drink.domain.DrinkIngredientsFilled;
import com.qnnect.drink.domain.UserDrinkSelected;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@ApiModel
public class StampResponse {
    private String cafeName;
    private String drinkName;

    public static StampResponse from(UserDrinkSelected userDrinkSelected) {
        return StampResponse.builder()
                .cafeName(userDrinkSelected.getCafeUser().getCafe().getTitle())
                .drinkName(userDrinkSelected.getDrink().getName())
                .build();
    }

    public static List<StampResponse> listFrom(List<UserDrinkSelected> userDrinkSelectedList) {
        return userDrinkSelectedList.stream()
                .map(StampResponse::from)
                .collect(Collectors.toList());
    }
}
