package com.qnnect.questions.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class QuestionRequest {
    @ApiModelProperty(value = "내용", example = "친구와 함께 가장 가고 싶은 외국 여행지는 어딘가요?")
    private String content;
}
