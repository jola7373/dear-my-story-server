package com.qnnect.questions.dto;

import com.qnnect.cafe.domain.Cafe;
import com.qnnect.questions.domain.EQuestionerType;
import com.qnnect.questions.domain.Question;
import com.qnnect.questions.service.CafeQuestionService;
import com.qnnect.questions.service.CafeQuestionServiceImpl;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TodayQuestionResponse {

    private final CafeQuestionService cafeQuestionService = null;

    @ApiModelProperty(value = "오늘의 질문 id", example = "1")
    private Long id;

    @ApiModelProperty(value = "내용", example = "친구와 함께 가장 가고 싶은 외국 여행지는 어딘가요?")
    private String content;

    public TodayQuestionResponse(Cafe cafe){
        if(cafe == null){
            System.out.println("cafe is null");
        }
        Question question = cafeQuestionService.findQuestionToday(cafe);
        if(question == null){
            this.id = 1L;
            this.content = null;
        }
        this.id = question.getId();
        this.content = question.getContent();
    }
}
