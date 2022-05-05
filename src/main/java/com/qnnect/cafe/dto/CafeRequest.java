package com.qnnect.cafe.dto;

import com.qnnect.cafe.domain.Cafe;
import com.qnnect.cafe.domain.EDiaryColor;
import com.qnnect.cafe.domain.EGroupType;
import com.qnnect.cafe.domain.EQuestionCycle;
import com.qnnect.user.domain.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import org.apache.commons.lang3.RandomStringUtils;

@ApiModel(value="다이어리 생성")
@Getter
public class CafeRequest {

    @ApiModelProperty(value = "다이어리 제목", example = "신사고 4인방")
    private String title;

    @ApiModelProperty(value = "함께 하는 사람", example = "친구")
    private EGroupType groupType;

    @ApiModelProperty(value = "질문 주기", example = "everyDay")
    private EQuestionCycle questionCycle;

    @ApiModelProperty(value = "다이어리 색", example = "red")
    private EDiaryColor diaryColor;

    public Cafe toEntity() {
        return Cafe.builder().title(title).groupType(groupType).questionCycle(questionCycle)
                .diaryColor(diaryColor).code(generateCode()).build();
    }

    public String generateCode(){
        String generatedString = RandomStringUtils.randomAlphanumeric(8);
        return generatedString;
    }

}
