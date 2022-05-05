package com.qnnect.likes;

import com.qnnect.questions.domain.Question;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel(value="좋아요 랭킹")
@Getter
public class LikeRankingResponse {

    @ApiModelProperty(value = "랭킹", example = "1")
    int rank;

    @ApiModelProperty(value = "질문", example = "함께 가고 싶은 여행지는 어딘가요?")
    Question questions;
}
