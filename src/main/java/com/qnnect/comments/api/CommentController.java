package com.qnnect.comments.api;

import com.qnnect.comments.domain.Comment;
import com.qnnect.comments.dtos.CommentDetailResponse;
import com.qnnect.comments.dtos.CommentUpdateRequest;
import com.qnnect.comments.dtos.ContentDto;
import com.qnnect.comments.service.CommentService;
import com.qnnect.common.CurrentUser;
import com.qnnect.user.domain.User;
import io.grpc.internal.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Api(tags = {"댓글 관련 API"})
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/questions/{cafeQuestionId}/comments")
    @ApiOperation(value = "댓글 생성 api")
    public ResponseEntity<Long> createComment(@PathVariable Long cafeQuestionId,
                                              @ApiIgnore @CurrentUser User user,
                                              @RequestPart(required = true) String content,
                                              @RequestPart(required = false) MultipartFile image1,
                                              @RequestPart(required = false) MultipartFile image2,
                                              @RequestPart(required = false) MultipartFile image3,
                                              @RequestPart(required = false) MultipartFile image4,
                                              @RequestPart(required = false) MultipartFile image5
                                              ){

        Long commentId = commentService.create(cafeQuestionId, user, content, image1,
                image2, image3, image4, image5);
        return ResponseEntity.ok(commentId);
    }

    @PatchMapping("/comments/{commentId}")
    @ApiOperation(value = "댓글 수정 api")
    public ResponseEntity<Void> updateComment(@PathVariable Long commentId,
                                              @ApiIgnore @CurrentUser User user,
                                              @RequestPart(required = true) String content,
                                              @RequestPart(required = false) MultipartFile image1,
                                              @RequestPart(required = false) MultipartFile image2,
                                              @RequestPart(required = false) MultipartFile image3,
                                              @RequestPart(required = false) MultipartFile image4,
                                              @RequestPart(required = false) MultipartFile image5){

        commentService.update(commentId, user, content, image1,
                image2, image3, image4, image5);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/comments/{commentId}/content")
    @ApiOperation(value = "댓글 수정 api")
    public ResponseEntity<Void> updateCommentContent(@PathVariable Long commentId,
                                              @ApiIgnore @CurrentUser User user,
                                              @RequestBody ContentDto content){
        System.out.println("controller");
        commentService.update(commentId, content.getContent());
        return ResponseEntity.ok().build();
    }

    @PatchMapping(path = "/comments/{commentId}/image")
    @ApiOperation(value = "댓글 이미지 수정 api")
    public ResponseEntity<Void> updateComment(@PathVariable Long commentId,
                                              @ApiIgnore @CurrentUser User user,
                                              @RequestPart(required = false) MultipartFile image1,
                                              @RequestPart(required = false) MultipartFile image2,
                                              @RequestPart(required = false) MultipartFile image3,
                                              @RequestPart(required = false) MultipartFile image4,
                                              @RequestPart(required = false) MultipartFile image5 ,
                                              @RequestPart(required = false) String isImageEmpty1,
                                              @RequestPart(required = false) String isImageEmpty2,
                                              @RequestPart(required = false) String isImageEmpty3,
                                              @RequestPart(required = false) String isImageEmpty4,
                                              @RequestPart(required = false) String isImageEmpty5
                                              ){
        System.out.println("test");
        commentService.update(commentId, image1, image2, image3,image4, image5,
                isImageEmpty1, isImageEmpty2,isImageEmpty3,isImageEmpty4,isImageEmpty5);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/comments/{commentId}")
    @ApiOperation(value = "댓글 삭제 api")
    public ResponseEntity<Void> deleteComment(
                                              @PathVariable Long commentId){
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/comments/{commentId}")
    @ApiOperation(value = "댓글 가져오기 api(대댓글 포함)")
    public ResponseEntity<CommentDetailResponse> getComment(@PathVariable Long commentId,
                                                            @ApiIgnore @CurrentUser User user){
        CommentDetailResponse commentDetailResponse = commentService.getComment(commentId, user);
        return ResponseEntity.ok(commentDetailResponse);
    }

}
