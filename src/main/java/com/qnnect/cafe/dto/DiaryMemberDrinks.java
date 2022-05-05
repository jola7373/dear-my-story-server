package com.qnnect.cafe.dto;


import com.qnnect.drink.domain.Drink;
import com.qnnect.user.domain.User;
import io.swagger.annotations.ApiModel;
import lombok.Getter;

@ApiModel(value="다이어리 사용자의 음료")
@Getter
public class DiaryMemberDrinks {

    private Long id;
    private User user;
    private Drink drink;
}
