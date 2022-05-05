package com.qnnect.questions.dto;

import com.qnnect.questions.domain.CafeQuestion;
import com.qnnect.questions.domain.CafeQuestionWaitingList;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CafeQuestionResponse {

    private List<QuestionResponse> cafeQuestionList;
//    private List<QuestionResponse> cafeQuestionWaitingList;

    public static CafeQuestionResponse from(List<CafeQuestion> cafeQuestionList){
//                                            List<CafeQuestionWaitingList> cafeQuestionWaitingList) {

        return CafeQuestionResponse.builder()
                .cafeQuestionList(QuestionResponse.listFromCafeQuestion(cafeQuestionList))
//                .cafeQuestionWaitingList(QuestionResponse.listFromCafeQuestionWaitingList(cafeQuestionWaitingList))
                .build();
    }

}
