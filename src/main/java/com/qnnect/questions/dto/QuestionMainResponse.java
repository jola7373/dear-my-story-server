package com.qnnect.questions.dto;

import com.qnnect.questions.domain.CafeQuestion;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Builder
@ApiModel(value="질문")
public class QuestionMainResponse {

    @ApiModelProperty(value = "다이어리 질문 리스트 id", example = "1")
    private Long cafeQuestionId;

    //string으로 처리 하자.
    @ApiModelProperty(value = "답변 가능 날 수")
    private long daysLeft;

    @ApiModelProperty(value = "내용", example = "친구와 함께 가장 가고 싶은 외국 여행지는 어딘가요?")
    private String content;

    @ApiModelProperty(value="카페 이름", example = "신사고 4인방")
    private String cafeTitle;



    public static QuestionMainResponse from(CafeQuestion cafeQuestion) {
        if(cafeQuestion == null){
            return null;
        }
        return QuestionMainResponse.builder()
                .cafeQuestionId(cafeQuestion.getId())
                .content(cafeQuestion.getQuestions().getContent())
                .cafeTitle(cafeQuestion.getCafe().getTitle())
                .daysLeft(calculateDaysLeft(cafeQuestion))
                .build();
    }

    public static long calculateDaysLeft(CafeQuestion cafeQuestion){
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(cafeQuestion.getCreatedAt(),now);
        Long daysLeft = 7 - duration.toDays();
        return daysLeft;
    }

    public static List<QuestionMainResponse> listFrom(List<CafeQuestion> cafeQuestionList) {
        if(cafeQuestionList == null){
            return null;
        }

        return cafeQuestionList.stream()
                .map(QuestionMainResponse::from)
                .filter(questionMainResponse -> questionMainResponse != null)
                .collect(Collectors.toList());
    }
}
