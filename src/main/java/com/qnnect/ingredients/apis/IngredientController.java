package com.qnnect.ingredients.apis;

import com.qnnect.common.CurrentUser;
import com.qnnect.drink.dtos.DrinkResponse;
import com.qnnect.ingredients.domain.EIngredientType;
import com.qnnect.ingredients.dto.IngredientResponse;
import com.qnnect.ingredients.dto.MyIngredientResponse;
import com.qnnect.ingredients.service.IngredientService;
import com.qnnect.user.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Api(tags = {"재료 관련 API"})
public class IngredientController {

    private final IngredientService ingredientService;

    @ApiOperation(value = "전체 재료 보기 api")
    @GetMapping("/ingredients/all")
    public ResponseEntity<List<IngredientResponse>> getIngredientList(){
        List <IngredientResponse> ingredientResponses = ingredientService.getIngredients();
        return ResponseEntity.ok(ingredientResponses);
    }

    @ApiOperation(value = "종류별 재료 보기 api")
    @GetMapping("/ingredients/{ingredientType}")
    public ResponseEntity<List<IngredientResponse>> getIngredientByType(@PathVariable EIngredientType ingredientType){
        List <IngredientResponse> ingredientResponses = ingredientService.getIngredientsByType(ingredientType);
        return ResponseEntity.ok(ingredientResponses);
    }

    @ApiOperation(value = "재료 구매 api")
    @PostMapping("/ingredients/{ingredientsId}")
    public ResponseEntity<Void> buyIngredients(@PathVariable Long ingredientsId, @ApiIgnore @CurrentUser User user){
        ingredientService.buyIngredients(ingredientsId, user);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "내가 구매한 재료(전체) api")
    @GetMapping("/ingredients/me/all")
    public ResponseEntity<List<MyIngredientResponse>> myIngredients(@ApiIgnore @CurrentUser User user){
         List<MyIngredientResponse> myIngredientResponses = ingredientService.getAllMyIngredients(user);
        return ResponseEntity.ok(myIngredientResponses);
    }

    @ApiOperation(value = "내가 구매한 재료 종류별 api")
    @GetMapping("/ingredients/me/{ingredientType}")
    public ResponseEntity<List<MyIngredientResponse>> myIngredients(@ApiIgnore @CurrentUser User user, @PathVariable EIngredientType ingredientType){
        List<MyIngredientResponse> myIngredientResponses = ingredientService.getMyIngredientsByType(user, ingredientType);
        return ResponseEntity.ok(myIngredientResponses);
    }

}
