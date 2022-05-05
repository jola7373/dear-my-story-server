package com.qnnect.cafe.api;

import com.qnnect.cafe.dto.DiaryMemberDrinks;
import com.qnnect.common.CurrentUser;
import com.qnnect.drink.service.CafeDrinkService;
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
@Api(tags ="카페 사용자의 음료 관련 API")
public class CafeDrinkController {

    private final CafeDrinkService cafeDrinkService;

    @ApiOperation(value = "카페 음료 선택 api")
    @PostMapping("/diaries/{cafeId}/drinks")
    public ResponseEntity<Void> addDrink(@RequestParam Long drinkId, @PathVariable Long cafeId,
                                         @ApiIgnore @CurrentUser User user){
        cafeDrinkService.addCafeDrinks(user, cafeId, drinkId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "다이어리 음료 보기 api")
    @GetMapping("/diaries/{diaryId}/drinks")
    public ResponseEntity<List<DiaryMemberDrinks>> getDrink(@PathVariable Long diaryId){
        List<DiaryMemberDrinks> diaryMemberDrinksList = new ArrayList<>();
        return ResponseEntity.ok(diaryMemberDrinksList);
    }
}
