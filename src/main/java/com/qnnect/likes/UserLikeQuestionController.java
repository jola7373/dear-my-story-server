package com.qnnect.likes;

import com.qnnect.common.CurrentUser;
import com.qnnect.user.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Api(tags = {"좋아요 API"})
public class UserLikeQuestionController {

    private final UserLikeQuestionService userLikeQuestionService;

    @PostMapping("/users/question/{cafeQuestionId}")
    @ApiOperation(value = "좋아요 하기 api 및 취소")
    public ResponseEntity<Void> likeQuestion(@ApiIgnore @CurrentUser User user, @PathVariable Long cafeQuestionId,
                                             @RequestParam boolean isUserLiked){
        userLikeQuestionService.likeQuestion(isUserLiked, user,cafeQuestionId);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/question/like")
    @ApiOperation(value = "좋아요 많이 받은 질문 api")
    public ResponseEntity<List<LikeRankingResponse>> rankingQuestion(){
        List<LikeRankingResponse> likeRankingResponses = new ArrayList<>();
        return ResponseEntity.ok(likeRankingResponses);
    }
}
