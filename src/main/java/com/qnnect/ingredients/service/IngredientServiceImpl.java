package com.qnnect.ingredients.service;

import com.qnnect.common.exception.CustomException;
import com.qnnect.ingredients.domain.EIngredientType;
import com.qnnect.ingredients.domain.Ingredient;
import com.qnnect.ingredients.domain.UserIngredient;
import com.qnnect.ingredients.dto.IngredientResponse;
import com.qnnect.ingredients.dto.MyIngredientResponse;
import com.qnnect.ingredients.repository.IngredientRepository;
import com.qnnect.ingredients.repository.UserIngredientRepository;
import com.qnnect.user.domain.User;
import com.qnnect.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

import static com.qnnect.common.exception.ErrorCode.INGREDIENT_NOT_FOUND;
import static com.qnnect.common.exception.ErrorCode.POINT_NOT_ENOUGH;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService{

    private final IngredientRepository ingredientRepository;
    private final UserIngredientRepository userIngredientRepository;
    private final UserRepository userRepository;

    public List<IngredientResponse> getIngredients(){
        List<Ingredient> ingredients = ingredientRepository.findAll();
        return IngredientResponse.listFrom(ingredients);
    }

    public List<IngredientResponse> getIngredientsByType(EIngredientType ingredientType){
        List<Ingredient> ingredients = ingredientRepository.findAllByIngredientType(ingredientType);
        return IngredientResponse.listFrom(ingredients);
    }

    @Transactional
    public void buyIngredients(Long ingredientId, User user){
        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new CustomException(INGREDIENT_NOT_FOUND));
        if(user.getPoint() < ingredient.getPoint()){
            throw new CustomException(POINT_NOT_ENOUGH);
        }
        user.minusPoint(ingredient.getPoint());
        UserIngredient userIngredient = userIngredientRepository.save(UserIngredient.builder().user(user)
                .ingredient(ingredient).build());
        userRepository.save(user);
    }

    public List<MyIngredientResponse> getAllMyIngredients(User user){
        List<Object[]> userIngredients = userIngredientRepository.countByIngredientWhereUser_Id(user.getId());
        return MyIngredientResponse.listfrom(userIngredients);
    }

    public List<MyIngredientResponse> getMyIngredientsByType(User user, EIngredientType ingredientType){
        List<Object[]> userIngredients = userIngredientRepository.countByIngredientWhereUser_IdAndType(user.getId(), ingredientType);
        return MyIngredientResponse.listfrom(userIngredients);
    }
}
