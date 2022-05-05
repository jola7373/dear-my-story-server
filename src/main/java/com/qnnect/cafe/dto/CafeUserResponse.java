package com.qnnect.cafe.dto;

import com.qnnect.cafe.domain.CafeUser;
import com.qnnect.drink.domain.DrinkIngredientsFilled;
import com.qnnect.drink.domain.DrinkRecipe;
import com.qnnect.drink.domain.UserDrinkSelected;
import com.qnnect.drink.dtos.CafeDrinkCommonResponse;
import com.qnnect.drink.dtos.DrinkIngredientsFilledResponse;
import com.qnnect.drink.repository.UserDrinkSelectedRepository;
import com.qnnect.user.domain.User;
import com.qnnect.user.dtos.ProfileResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Builder
public class CafeUserResponse {
    private ProfileResponse user;
//    private String userDrinkSelected;
//    private List<DrinkIngredientsFilledResponse> drinkIngredientsFilledResponseList;
    private CafeDrinkCommonResponse cafeDrinkCommonResponse;

    public static CafeUserResponse from(CafeUser cafeUser, UserDrinkSelected userDrinkSelected) {
        List<DrinkIngredientsFilled> drinkIngredientsFilled = new ArrayList<>();
        List<DrinkRecipe> drinkRecipe = null;
        if(userDrinkSelected != null){
            drinkRecipe = userDrinkSelected.getDrink().getDrinkRecipeList();
        }

        return CafeUserResponse.builder()
                .user(ProfileResponse.from(cafeUser.getUser()))
                .cafeDrinkCommonResponse(new CafeDrinkCommonResponse(userDrinkSelected, drinkRecipe, drinkIngredientsFilled.size()))
                .build();
    }


    public static List<CafeUserResponse> listFrom(List<CafeUser> cafeUsers,
                                                  CafeUser currentCafeUser, List<Long> reportedId) {

        List<CafeUserResponse> cafeUserResponseList = new ArrayList<>();

        cafeUserResponseList.addAll((cafeUsers.stream()
                .filter(cafeUser -> cafeUser.getUser() != currentCafeUser.getUser())
                .filter(cafeUser ->  !reportedId.contains(cafeUser.getUser().getReportId()))
                .filter(cafeUser -> cafeUser.getUserDrinkSelected().size() == 0)
                .map(cafeUser -> from(cafeUser,null))
                .collect(Collectors.toList())));

        cafeUserResponseList.addAll(cafeUsers.stream()
                .filter(cafeUser -> cafeUser.getUser() != currentCafeUser.getUser())
                .filter(cafeUser ->  !reportedId.contains(cafeUser.getUser().getReportId()))
                .filter(cafeUser -> cafeUser.getUserDrinkSelected().size() != 0)
                .map(cafeUser -> from(cafeUser,cafeUser.getUserDrinkSelected().get(cafeUser.getUserDrinkSelected().size()-1)))
                .collect(Collectors.toList()));

        return cafeUserResponseList;
    }
}
