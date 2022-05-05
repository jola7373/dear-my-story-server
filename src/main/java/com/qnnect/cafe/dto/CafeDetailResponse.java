package com.qnnect.cafe.dto;

import com.qnnect.cafe.domain.Cafe;

import com.qnnect.cafe.domain.CafeUser;
import com.qnnect.cafe.domain.EDiaryColor;
import com.qnnect.user.domain.User;
import io.swagger.annotations.ApiModel;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@ApiModel(value = "카페")
public class CafeDetailResponse {

    private long cafeId;

    private long cafeUserId;

    private LocalDate createdAt;

    private String title;
    
    private String code;

    private EDiaryColor diaryColor;

    private CafeUserResponse currentUserResponse;

    private List<CafeUserResponse> cafeUserResponseList;

    private List<OneCafeQuestionResponse> cafeQuestionResponseList;

    public CafeDetailResponse(Cafe entity, CafeUser currentCafeUser, User user, List<Long> reportedId){
        this.createdAt = entity.getCreatedAt().toLocalDate();
        this.title = entity.getTitle();
        this.code = entity.getCode();
        this.cafeId = entity.getId();
        this.cafeUserId = currentCafeUser.getId();
        this.diaryColor = entity.getDiaryColor();
        if(currentCafeUser.getUserDrinkSelected() == null || currentCafeUser.getUserDrinkSelected().size() == 0){
            this.currentUserResponse = CafeUserResponse.from(currentCafeUser,null);
        }else{
            this.currentUserResponse = CafeUserResponse.from(currentCafeUser, currentCafeUser.getUserDrinkSelected().get(currentCafeUser.getUserDrinkSelected().size()-1));
        }
        this.cafeQuestionResponseList = OneCafeQuestionResponse.listFrom(entity.getCafeQuestions(), user, reportedId);
        for(int i=0; i< entity.getCafeUsers().size(); i++){
            System.out.println(entity.getCafeUsers().get(i).getUser().getNickName());
        }
        this.cafeUserResponseList = CafeUserResponse.listFrom(entity.getCafeUsers(), currentCafeUser, reportedId);
    }
}
