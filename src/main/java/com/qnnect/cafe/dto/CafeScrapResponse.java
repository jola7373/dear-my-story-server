package com.qnnect.cafe.dto;

import com.qnnect.cafe.domain.Cafe;
import com.qnnect.cafe.domain.CafeUser;
import com.qnnect.drink.domain.DrinkIngredientsFilled;
import com.qnnect.drink.domain.UserDrinkSelected;
import com.qnnect.drink.dtos.DrinkIngredientsFilledResponse;
import com.qnnect.user.dtos.ProfileResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class CafeScrapResponse {

    Long cafeId;
    String cafeTitle;

    public static CafeScrapResponse from(Cafe cafe) {
        return CafeScrapResponse.builder()
                .cafeId(cafe.getId())
                .cafeTitle(cafe.getTitle())
                .build();
    }


    public static List<CafeScrapResponse> listFrom(List<Cafe> cafe) {

        return cafe.stream()
                .map(CafeScrapResponse::from)
                .collect(Collectors.toList());
    }
}
