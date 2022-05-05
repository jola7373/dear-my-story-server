package com.qnnect.questions.apis;

import com.qnnect.common.CurrentUser;
import com.qnnect.questions.dto.QuestionRequest;
import com.qnnect.questions.service.CafeQuestionService;
import com.qnnect.user.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Api(tags = {"사용자 질문 생성관련 API"})
public class UserQuestionController {

    private final CafeQuestionService cafeQuestionService;

    @PostMapping("/cafes/{cafeId}/question/")
    @ApiOperation(value = "사용자 질문 생성 api")
    public ResponseEntity<Long> createQuestion(@PathVariable Long cafeId, @RequestBody String content, @ApiIgnore @CurrentUser User user){
        Long questionId = cafeQuestionService.create(cafeId, content,user );
        return ResponseEntity.ok(questionId);
    }

    @PatchMapping("/question/{cafeQuestionId}")
    @ApiOperation(value = "사용자 질문 수정 api")
    public ResponseEntity<Void> updateQuestion(@PathVariable Long cafeQuestionId,@RequestBody String content){
        cafeQuestionService.update(cafeQuestionId, content);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/question/{cafeQuestionId}")
    @ApiOperation(value = "사용자 질문 삭제 api")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long cafeQuestionId){
        cafeQuestionService.delete(cafeQuestionId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/my/question/{questionId}")
    @ApiOperation(value = "사용자 질문 수정 api[아직 보내지지 않은 질문]")
    public ResponseEntity<Void> updateQuestionWaiting(@PathVariable Long questionId, @RequestBody QuestionRequest questionRequest){
        cafeQuestionService.updateWaiting(questionId, questionRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/my/question/{questionId}")
    @ApiOperation(value = "사용자 질문 삭제 api[아직 보내지지 않은 질문]")
    public ResponseEntity<Void> deleteQuestionWaiting(@PathVariable Long questionId){
        cafeQuestionService.deleteWaiting(questionId);
        return ResponseEntity.noContent().build();
    }
}
