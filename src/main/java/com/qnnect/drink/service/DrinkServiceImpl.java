package com.qnnect.drink.service;

import com.qnnect.cafe.domain.CafeUser;
import com.qnnect.cafe.repository.CafeUserRepository;
import com.qnnect.common.exception.CustomException;
import com.qnnect.common.exception.ErrorCode;
import com.qnnect.drink.domain.DrinkIngredientsFilled;
import com.qnnect.drink.domain.DrinkRecipe;
import com.qnnect.drink.domain.UserDrinkSelected;
import com.qnnect.drink.dtos.CafeDrinkRecipeResponse;
import com.qnnect.drink.dtos.DrinkResponse;
import com.qnnect.drink.dtos.StampResponse;
import com.qnnect.drink.repository.DrinkIngredientsFilledRepository;
import com.qnnect.drink.repository.DrinkRecipeRepository;
import com.qnnect.drink.repository.DrinkRepository;
import com.qnnect.drink.repository.UserDrinkSelectedRepository;
import com.qnnect.ingredients.domain.Ingredient;
import com.qnnect.ingredients.domain.UserIngredient;
import com.qnnect.ingredients.repository.IngredientRepository;
import com.qnnect.ingredients.repository.UserIngredientRepository;
import com.qnnect.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class DrinkServiceImpl implements DrinkService {

    private final DrinkRepository drinkRepository;
    private final UserDrinkSelectedRepository userDrinkSelectedRepository;
    private final DrinkIngredientsFilledRepository drinkIngredientsFilledRepository;
    private final DrinkRecipeRepository drinkRecipeRepository;
    private final UserIngredientRepository userIngredientRepository;
    private final IngredientRepository ingredientRepository;
    private final CafeUserRepository cafeUserRepository;


    @Override
    public List<DrinkResponse> getDrinkList() {
        List<DrinkResponse> drinkResponses = DrinkResponse.listFrom(drinkRepository.findAll());
        return drinkResponses;
    }

    @Override
    public CafeDrinkRecipeResponse getDrinkRecipes(User user, long userSelectedDrinkId, long cafeId) {
        CafeUser currentUser = cafeUserRepository.findByCafe_IdAndUser_Id(cafeId, user.getId());
        List<UserDrinkSelected> userDrinkSelected = userDrinkSelectedRepository.findAllByCafeUser_Id(currentUser.getId());
        List<DrinkRecipe> drinkRecipes = null;
        int size = 0;

        if(userDrinkSelected.size()!=0){
            drinkRecipes = drinkRecipeRepository.findAllByDrink_Id(userDrinkSelected.get(userDrinkSelected.size()-1).getDrink().getId());
            size = currentUser.getUserDrinkSelected().get(currentUser.getUserDrinkSelected().size()-1).getDrinkIngredientsFilled().size();
        }
        return new CafeDrinkRecipeResponse(currentUser, drinkRecipes, size);
    }



    @Override
    public List<StampResponse> getStampUser(User user) {
        List<CafeUser> cafeUsers = cafeUserRepository.findAllByUser_Id(user.getId());
        List<UserDrinkSelected> userDrinkSelectedList = new ArrayList<UserDrinkSelected>();
        for(int i=0; i < cafeUsers.size(); i++){
            if(cafeUsers.get(i).getUserDrinkSelected().size() != 0){
                userDrinkSelectedList.addAll(cafeUsers.get(i).getUserDrinkSelected()
                        .stream().filter(cafeUser-> cafeUser.isFilled())
                        .collect(Collectors.toList()));
            }

        }
        return StampResponse.listFrom(userDrinkSelectedList);
    }

    @Override
    public void addIngredient(Long userDrinkSelectedId, Long ingredientsId, User user) {
        UserDrinkSelected userDrinkSelected = userDrinkSelectedRepository.getById(userDrinkSelectedId);
        long count = drinkIngredientsFilledRepository.countByUserDrinkSelected_Id(userDrinkSelectedId);
        System.out.println(userDrinkSelected.getDrink());
        List<DrinkRecipe> drinkRecipe = drinkRecipeRepository.findAllByDrink_Id(userDrinkSelected.getDrink().getId());
        int recipecount = 0;
        int idx = -1;

        if(userDrinkSelected.isFilled()){
            throw new CustomException(ErrorCode.WRONG_INGREDIENT_DIFFERENT_LEVEL);
        }

        while (count >= recipecount && idx < 3) {
            idx++;
            System.out.println(drinkRecipe.get(idx).getIngredient().getName());
            recipecount += drinkRecipe.get(idx).getNumber();
        }

        Ingredient currIngredientLevel = drinkRecipe.get(idx).getIngredient();

        Pageable pageable = PageRequest.of(0, 1);
        List<UserIngredient> userIngredient = userIngredientRepository.getByUser_IdAndIngredient_Id(user.getId(), ingredientsId, pageable);
        System.out.println(userIngredient.size());
        if (currIngredientLevel.getId() == ingredientsId) {// 성공시 로직
            System.out.println("right ingredient");
            putIngredients(currIngredientLevel, userDrinkSelected, userIngredient);
        } else {
            System.out.println(currIngredientLevel.getId());
            wrongIngredients(currIngredientLevel, ingredientsId, userIngredient);
        }

    }

    @Transactional
    public void putIngredients(Ingredient currIngredientLevel, UserDrinkSelected userDrinkSelected,
                               List<UserIngredient> userIngredient) {
        log.info("FIlling user Drink");
        drinkIngredientsFilledRepository.save(DrinkIngredientsFilled.builder()
                .ingredient(currIngredientLevel)
                .userDrinkSelected(userDrinkSelected).build());
        if(drinkIngredientsFilledRepository.countByUserDrinkSelected_Id(userDrinkSelected.getId()) == 10){
            userDrinkSelected.setFilled(true);
        }
        userIngredientRepository.deleteById(userIngredient.get(0).getId());

        log.info("SUCCEED on adding ingredients");
        return;
    }

    public void wrongIngredients(Ingredient currIngredientLevel, long ingredientsId
            , List<UserIngredient> userIngredient) {
        ArrayList<String> ingredientLevel = new ArrayList<>();
        ingredientLevel.add("ice");
        ingredientLevel.add("base");
        ingredientLevel.add("main");
        ingredientLevel.add("topic");
        ingredientLevel.add("ice_base");


        int currIdx = ingredientLevel.indexOf(currIngredientLevel.getIngredientType().toString());
        System.out.println("current" + currIdx);

        Ingredient userPutIngredient = ingredientRepository.getById(ingredientsId);

        int puttingIdx = ingredientLevel.indexOf(userPutIngredient.getIngredientType().toString());
        System.out.println("putting" + puttingIdx);

        if(currIdx == 4){
            if(currIngredientLevel.getId() == 1){
                currIdx = 0;
            }else {
                currIdx = 1;
            }
        }

        if(puttingIdx == 4){
            if(ingredientsId == 1){
                puttingIdx = 0;
            }else {
                puttingIdx = 1;
            }
        }

        if (currIdx == puttingIdx) {
            //같은 레벨의 다른 재료를 소진한경우 재료만 소진되고 throw exception
            System.out.println("같은 레벨");
            userIngredientRepository.deleteById(userIngredient.get(0).getId());
            throw new CustomException(ErrorCode.WRONG_INGREDIENT_SAME_LEVEL);
        }else{
            throw new CustomException(ErrorCode.WRONG_INGREDIENT_DIFFERENT_LEVEL);
        }
    }

}
