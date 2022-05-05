package com.qnnect.questions.dto;

import com.qnnect.questions.domain.CafeQuestion;
import com.qnnect.questions.domain.CafeQuestionWaitingList;
import lombok.Builder;
import lombok.Getter;


import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Builder
public class MyQuestionResponse {

    private Long cafeQuestionId;

    private LocalDate createdAt;

    private String question;

    private String cafeTitle;

    private boolean isWaitingList;


    public static MyQuestionResponse fromCafeQuestion(CafeQuestion cafeQuestion){
        if(cafeQuestion == null){
            return null;
        }

        return MyQuestionResponse.builder()
                .cafeQuestionId(cafeQuestion.getId())
                .createdAt(cafeQuestion.getQuestions().getCreatedAt().toLocalDate())
                .question(cafeQuestion.getQuestions().getContent())
                .isWaitingList(false)
                .cafeTitle(cafeQuestion.getCafe().getTitle())
                .build();
    }

    public static MyQuestionResponse fromWaitingList(CafeQuestionWaitingList cafeQuestionListWaitingList){

        return MyQuestionResponse.builder()
                .createdAt(cafeQuestionListWaitingList.getCreatedAt().toLocalDate())
                .isWaitingList(true)
                .cafeQuestionId(cafeQuestionListWaitingList.getQuestion().getId())
                .question(cafeQuestionListWaitingList.getQuestion().getContent())
                .cafeTitle(cafeQuestionListWaitingList.getCafe().getTitle())
                .build();
    }

    public static List<MyQuestionResponse> listFromAllQuestions(List<CafeQuestion> cafeQuestionList,
                                                              List<CafeQuestionWaitingList> cafeQuestionWaitingList) {
        List<MyQuestionResponse> cafeQuestionResponse = new ArrayList<>();
        List<MyQuestionResponse> cafeQuestionWaitingResponse = new ArrayList<>();

        if(cafeQuestionWaitingList.size() != 0){
            cafeQuestionWaitingResponse = cafeQuestionWaitingList.stream()
                    .map(MyQuestionResponse::fromWaitingList)
                    .collect(Collectors.toList());
        }
        if(cafeQuestionList.size() != 0){
            cafeQuestionResponse = cafeQuestionList.stream()
                    .map(MyQuestionResponse::fromCafeQuestion)
                    .collect(Collectors.toList());
        }

        List<MyQuestionResponse> totalQuestionResponse = Stream.of(cafeQuestionWaitingResponse, cafeQuestionResponse)
                .flatMap(x -> x.stream())
                .collect(Collectors.toList());

        return totalQuestionResponse;
    }
}
