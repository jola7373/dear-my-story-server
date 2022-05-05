package com.qnnect.drink.apis;

import com.qnnect.common.CurrentUser;
import com.qnnect.drink.dtos.CafeDrinkIngredientResponse;
import com.qnnect.drink.dtos.CafeDrinkRecipeResponse;
import com.qnnect.drink.dtos.CafeDrinkResponse;
import com.qnnect.drink.dtos.DrinkResponse;
import com.qnnect.drink.service.CafeDrinkService;
import com.qnnect.drink.service.DrinkService;
import com.qnnect.user.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Api(tags = {"음료 관련 API"})
public class DrinkController {

    private final DrinkService drinkService;
    private final CafeDrinkService cafeDrinkService;

    @ApiOperation(value = "전체 음료리스트 보기 api")
    @GetMapping("/drinks")
    public ResponseEntity<List<DrinkResponse>> getDrinkList(){
        List <DrinkResponse> drinkResponses = drinkService.getDrinkList();
        return ResponseEntity.ok(drinkResponses);

    }

    @ApiOperation(value = "우리의 카페 api")
    @GetMapping("/cafe/{cafeId}/drink/{cafeUserId}")
    public ResponseEntity<CafeDrinkResponse> getCurrentDrink(@PathVariable long cafeId,
                                                                   @PathVariable long cafeUserId,
                                                                   @ApiIgnore @CurrentUser User user){
        CafeDrinkResponse cafeDrinkResponse = cafeDrinkService.getCurrentDrink(user,cafeUserId,cafeId);
        return ResponseEntity.ok(cafeDrinkResponse);

    }

    @ApiOperation(value = "음료에 재료 추가 api")
    @PatchMapping("/drinks/{userDrinkSelectedId}/ingredients/{ingredientsId}")
    public ResponseEntity<Void> addIngredientDrink(@PathVariable Long userDrinkSelectedId,
                                                   @PathVariable Long ingredientsId,
                                                   @ApiIgnore @CurrentUser User user){
        drinkService.addIngredient(userDrinkSelectedId, ingredientsId, user);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "음료와 레시피 가져오기 api")
    @GetMapping("/drinks/{userDrinkSelectedId}/recipe")
    public ResponseEntity<CafeDrinkRecipeResponse> getDrinkRecipe(@PathVariable Long userDrinkSelectedId,
                                                                  @RequestParam Long cafeId,
                                                                  @ApiIgnore @CurrentUser User user){
        CafeDrinkRecipeResponse cafeDrinkRecipeResponse = drinkService.getDrinkRecipes(user, userDrinkSelectedId, cafeId);
        return ResponseEntity.ok(cafeDrinkRecipeResponse);
    }

    @ApiOperation(value = "음료와 재료 api")
    @GetMapping("/cafe/{cafeId}/my_ingredient")
    public ResponseEntity<CafeDrinkIngredientResponse> getDrinkAndIngredients(@PathVariable Long cafeId,
                                                       @ApiIgnore @CurrentUser User user){
        CafeDrinkIngredientResponse cafeDrinkIngredientResponse =
                cafeDrinkService.getDrinkIngredient(user, cafeId);
        return ResponseEntity.ok(cafeDrinkIngredientResponse);
    }
}
