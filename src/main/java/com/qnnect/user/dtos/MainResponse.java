package com.qnnect.user.dtos;

import com.qnnect.cafe.domain.Cafe;
import com.qnnect.cafe.dto.CafeMainResponse;
import com.qnnect.questions.domain.CafeQuestion;
import com.qnnect.questions.dto.QuestionMainResponse;
import com.qnnect.user.domain.User;
import lombok.Getter;

import java.util.List;

@Getter
public class MainResponse {

    private ProfileResponse user;
    private List<QuestionMainResponse> questionTodayList;
    private List<CafeMainResponse> cafeMainResponseList;
    private boolean hasUnreadNotification;

    public MainResponse(User user, List<Cafe> cafe, List<CafeQuestion> todayQuestionList, boolean hasUnreadNotification){

        this.user = ProfileResponse.from(user);
        this.hasUnreadNotification = hasUnreadNotification;

        if(cafe == null){
            this.cafeMainResponseList = null;
        }else {
            this.cafeMainResponseList = CafeMainResponse.listFrom(cafe);
        }

        if (todayQuestionList == null){
            this.questionTodayList = null;
        }else{
            this.questionTodayList = QuestionMainResponse.listFrom(todayQuestionList);
        }
    }

}
