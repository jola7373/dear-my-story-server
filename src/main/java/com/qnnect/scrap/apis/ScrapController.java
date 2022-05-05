package com.qnnect.scrap.apis;

import com.qnnect.common.CurrentUser;
import com.qnnect.questions.dto.QuestionResponse;
import com.qnnect.scrap.service.ScrapService;
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
@Api(tags = {"스크랩 관련 API"})
public class ScrapController {

    private final ScrapService scrapService;

    @PostMapping("/users/scrap")
    @ApiOperation(value = "스크랩 하기 api")
    public ResponseEntity<Void> scrapQuestion(
            @RequestParam Long cafeQuestionId,@ApiIgnore @CurrentUser User user){
        scrapService.addScrap(user, cafeQuestionId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/scrap")
    @ApiOperation(value = "스크랩 취소 api")
    public ResponseEntity<Void> deleteScrapQuestion(
            @RequestParam Long cafeQuestionId, @ApiIgnore @CurrentUser User user){
        System.out.println("controller");
        scrapService.deleteScrap(user, cafeQuestionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/scrap/all")
    @ApiOperation(value = "전체 스크랩 리스트 가져오기 api")
    public ResponseEntity<List<QuestionResponse>> getAllScrapQuestion(@PageableDefault(sort="id", direction = Sort.Direction.DESC)final Pageable pageable, @ApiIgnore @CurrentUser User user){
        List<QuestionResponse> scrapResponseList = scrapService.getAllScraps(pageable, user);
        return ResponseEntity.ok(scrapResponseList);
    }

    @GetMapping("/users/scrap/{cafeId}")
    @ApiOperation(value = "카페별 스크랩 리스트 가져오기 api")
    public ResponseEntity<List<QuestionResponse>> getCafeScrapQuestion(@PathVariable Long cafeId, @PageableDefault(sort="id", direction = Sort.Direction.DESC)final Pageable pageable, @ApiIgnore @CurrentUser User user){
        List<QuestionResponse> scrapResponseList = scrapService.getCafeScraps(pageable,user, cafeId);
        return ResponseEntity.ok(scrapResponseList);
    }

    @GetMapping("/users/scrap/")
    @ApiOperation(value = "스크랩 검색 api")
    public ResponseEntity<List<QuestionResponse>> searchScrapQuestion(@RequestParam String searchWord, @PageableDefault(sort="id", direction = Sort.Direction.DESC)final Pageable pageable, @ApiIgnore @CurrentUser User user){
        List<QuestionResponse> scrapResponseList = scrapService.searchScraps( pageable,user, searchWord);
        return ResponseEntity.ok(scrapResponseList);
    }
}
