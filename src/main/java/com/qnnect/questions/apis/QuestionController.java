package com.qnnect.questions.apis;


import com.qnnect.cafe.dto.OneCafeQuestionResponse;
import com.qnnect.common.CurrentUser;
import com.qnnect.questions.dto.CafeQuestionResponse;
import com.qnnect.questions.dto.QuestionDetailResponse;
import com.qnnect.questions.dto.QuestionResponse;
import com.qnnect.questions.service.CafeQuestionService;
import com.qnnect.user.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Api(tags = {"질문 관련 API"})
public class QuestionController {

    private final CafeQuestionService cafeQuestionService;


//    @GetMapping("/diaries/{diaryId}/question/all")
//    @ApiOperation(value = "전체 질문")
//    public ResponseEntity<List<QuestionMainResponse>> getQuestionAll(@PathVariable Long diaryId){
//        List<QuestionMainResponse> questionResponse = new ArrayList<>();
//        return ResponseEntity.ok(questionResponse);
//    }

    @GetMapping("/question/{cafeQuestionId}")
    @ApiOperation(value = "질문 가져오기(댓글 함께) api")
    public ResponseEntity<QuestionDetailResponse> getQuestion(@PathVariable Long cafeQuestionId
            , @ApiIgnore @CurrentUser User user){
        QuestionDetailResponse questionDetailRespose = cafeQuestionService.getQuestion(cafeQuestionId,user);
        return ResponseEntity.ok(questionDetailRespose);
    }

    @GetMapping("/question/one/{cafeQuestionId}")
    @ApiOperation(value = "질문 가져오기 api")
    public ResponseEntity<OneCafeQuestionResponse> getOnlyQuestion(@PathVariable Long cafeQuestionId
            , @ApiIgnore @CurrentUser User user){
        OneCafeQuestionResponse cafeQuestionResponse = cafeQuestionService.getOneQuestion(cafeQuestionId,user);
        return ResponseEntity.ok(cafeQuestionResponse);
    }

    @GetMapping("/question/cafes/{cafeId}/all")
    @ApiOperation(value = "카페 질문 가져오기 api")
    public ResponseEntity<CafeQuestionResponse> getCafeQuestion(@PathVariable Long cafeId,
                                                                 @PageableDefault(sort="id", direction = Sort.Direction.DESC)final Pageable pageable){
        CafeQuestionResponse cafeQuestionResponse = cafeQuestionService.getCafeQuestions(cafeId, pageable);
        return ResponseEntity.ok(cafeQuestionResponse);
    }

    @GetMapping("/question/cafes/{cafeId}/search")
    @ApiOperation(value = "카페 질문 검색 api")
    public ResponseEntity<CafeQuestionResponse> searchCafeQuestion(@PathVariable Long cafeId,
                                                                   @RequestParam String searchWord,
                                                                    @PageableDefault(sort="id", direction = Sort.Direction.DESC)final Pageable pageable, @ApiIgnore @CurrentUser User user){
        CafeQuestionResponse cafeQuestionResponse = cafeQuestionService.searchCafeQuestions(cafeId, searchWord,pageable);
        return ResponseEntity.ok(cafeQuestionResponse);
    }
}
