package com.qnnect.ingredients.service;

import com.qnnect.ingredients.domain.EIngredientType;
import com.qnnect.ingredients.dto.IngredientResponse;
import com.qnnect.ingredients.dto.MyIngredientResponse;
import com.qnnect.user.domain.User;

import java.util.List;

public interface IngredientService {
    public List<IngredientResponse> getIngredients();
    public List<IngredientResponse> getIngredientsByType(EIngredientType ingredientType);
    public void buyIngredients(Long ingredientId, User user);
    public List<MyIngredientResponse> getAllMyIngredients(User user);
    public List<MyIngredientResponse> getMyIngredientsByType(User user, EIngredientType ingredientType);
}
