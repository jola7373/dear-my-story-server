package com.qnnect.comments.api;

import com.qnnect.comments.domain.Reply;
import com.qnnect.comments.dtos.ContentDto;
import com.qnnect.comments.service.ReplyService;
import com.qnnect.common.CurrentUser;
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
@Api(tags = {"대댓글 관련 API"})
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/comments/{commentId}/reply")
    @ApiOperation(value = "대댓글 생성 api")
    public ResponseEntity<Long> createReply(
                                            @PathVariable Long commentId,
                                            @RequestBody String content,
                                            @ApiIgnore @CurrentUser User user) {
        Reply reply = replyService.createReply(commentId, content, user);
        return ResponseEntity.ok(reply.getId());
    }

    @PatchMapping("/comments/{commentId}/reply/{replyId}")
    @ApiOperation(value = "대댓글 수정 api")
    public ResponseEntity<Void> updateReply(
                                            @PathVariable Long commentId,
                                            @PathVariable Long replyId,
                                            @RequestBody String content,
                                            @ApiIgnore @CurrentUser User user) {
        replyService.updateReply(replyId, content, user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/comments/{commentId}/reply/{replyId}")
    @ApiOperation(value = "대댓글 삭제 api")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId,
                                              @PathVariable Long replyId,
                                              @ApiIgnore @CurrentUser User user) {
        replyService.deleteReply(replyId, user);
        return ResponseEntity.noContent().build();
    }
}
