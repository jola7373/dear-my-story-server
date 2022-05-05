package com.qnnect.drink.service;

import com.qnnect.drink.dtos.CafeDrinkRecipeResponse;
import com.qnnect.drink.dtos.DrinkResponse;
import com.qnnect.drink.dtos.StampResponse;
import com.qnnect.user.domain.User;

import java.util.List;

public interface DrinkService {
    public List<DrinkResponse> getDrinkList();
    public void addIngredient(Long userDrinkSelectedId, Long ingredientsId, User user);
    public CafeDrinkRecipeResponse getDrinkRecipes(User user, long userSelectedDrinkId, long cafeId);
    public List<StampResponse> getStampUser(User user);

}
