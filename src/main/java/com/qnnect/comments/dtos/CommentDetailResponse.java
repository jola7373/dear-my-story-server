package com.qnnect.comments.dtos;

import com.qnnect.comments.domain.Comment;
import com.qnnect.comments.domain.Reply;
import com.qnnect.user.domain.User;
import com.qnnect.user.dtos.ProfileResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@ApiModel(value="댓글(대댓글 포함)")
@Getter
@Builder
public class CommentDetailResponse {

    Long commentId;
    Long cafeQuestionId;
    LocalDate createdAt;
    ProfileResponse writerInfo;
    String content;
    String imageUrl1;
    String imageUrl2;
    String imageUrl3;
    String imageUrl4;
    String imageUrl5;
    boolean isWriter;
    private List<ReplyResponse> replies;

    public static CommentDetailResponse from(Comment comment, User user, List<Reply> reply, List<Long>reportedUser) {

        return CommentDetailResponse.builder()
                .commentId(comment.getId())
                .cafeQuestionId(comment.getCafeQuestion().getId())
                .createdAt(comment.getCreatedAt().toLocalDate())
                .writerInfo(ProfileResponse.from(comment.getUser()))
                .content(comment.getContent())
                .imageUrl1(comment.getImageUrl1())
                .imageUrl2(comment.getImageUrl2())
                .imageUrl3(comment.getImageUrl3())
                .imageUrl4(comment.getImageUrl4())
                .imageUrl5(comment.getImageUrl5())
                .isWriter(isWriter(comment, user))
                .replies(ReplyResponse.listFrom(reply, user, reportedUser))
                .build();
    }

    public static boolean isWriter(Comment comment ,User user){
        return comment.getUser().getId().equals(user.getId());
    }

}
