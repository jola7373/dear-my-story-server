package com.qnnect.drink.service;

import com.qnnect.cafe.domain.CafeUser;
import com.qnnect.cafe.repository.CafeUserRepository;
import com.qnnect.drink.domain.DrinkRecipe;
import com.qnnect.drink.domain.UserDrinkSelected;
import com.qnnect.drink.dtos.CafeDrinkIngredientResponse;
import com.qnnect.drink.dtos.CafeDrinkResponse;
import com.qnnect.drink.repository.DrinkRecipeRepository;
import com.qnnect.drink.repository.DrinkRepository;
import com.qnnect.drink.repository.UserDrinkSelectedRepository;
import com.qnnect.ingredients.repository.IngredientRepository;
import com.qnnect.ingredients.repository.UserIngredientRepository;
import com.qnnect.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CafeDrinkServiceImpl implements CafeDrinkService{

    private final DrinkRepository drinkRepository;
    private final CafeUserRepository cafeUserRepository;
    private final UserDrinkSelectedRepository userDrinkSelectedRepository;
    private final DrinkRecipeRepository drinkRecipeRepository;
    private final UserIngredientRepository userIngredientRepository;

    @Transactional
    @Override
    public void addCafeDrinks(User user,Long cafeId, Long drinkId){

        CafeUser cafeUser = cafeUserRepository.findByCafe_IdAndUser_Id(cafeId,user.getId());
        //우선 userDrinkselected를 생성
        UserDrinkSelected userDrinkSelected = userDrinkSelectedRepository.save(UserDrinkSelected.builder()
                .cafeUser(cafeUser).drink(drinkRepository.getById(drinkId)).build());
//        //이후 해당 정보를 cafeUser에 저장해준다.
//        CafeUser cafeUser = cafeUserRepository.findByCafe_IdAndUser_Id(cafeId,
//                user.getId());
//        cafeUser.setUserDrinkSelected(userDrinkSelected);
//        cafeUserRepository.save(cafeUser);
    }

    @Transactional
    @Override
    public CafeDrinkResponse getCurrentDrink(User user, long cafeUserId, long cafeId){
        CafeUser drinkOwner = cafeUserRepository.getById(cafeUserId);
        List<UserDrinkSelected> drinkSelected = userDrinkSelectedRepository.findAllByCafeUser_Id(drinkOwner.getId());
        List<CafeUser> cafeUsers = cafeUserRepository.findAllByCafe_Id(drinkOwner.getCafe().getId());
        List<DrinkRecipe> drinkRecipe = drinkRecipeRepository.findAllByDrink_Id(drinkSelected.get(drinkOwner.getUserDrinkSelected().size()-1).getDrink().getId());
        int size = drinkSelected.get(drinkOwner.getUserDrinkSelected().size()-1).getDrinkIngredientsFilled().size();
        return new CafeDrinkResponse(drinkOwner.getUserDrinkSelected().get(drinkOwner.getUserDrinkSelected().size()-1),cafeUsers , user, drinkRecipe, size);
    }

    @Transactional
    @Override
    public CafeDrinkIngredientResponse getDrinkIngredient(User user, long cafeId){
        CafeUser currentUser = cafeUserRepository.findByCafe_IdAndUser_Id(cafeId,user.getId());

        List<UserDrinkSelected> drinkSelected = userDrinkSelectedRepository.findAllByCafeUser_Id(currentUser.getId());

        List<DrinkRecipe> drinkRecipe = drinkRecipeRepository.findAllByDrink_Id(currentUser.getUserDrinkSelected()
                .get(currentUser.getUserDrinkSelected().size()-1).getDrink().getId());
        List<Object[]> ingredients = userIngredientRepository.countByIngredientWhereUser_Id(user.getId());
        int size = currentUser.getUserDrinkSelected().get(currentUser.getUserDrinkSelected().size()-1).getDrinkIngredientsFilled().size();
        return new CafeDrinkIngredientResponse(currentUser, drinkRecipe, size, ingredients);
    }
}
