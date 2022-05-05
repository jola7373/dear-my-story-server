package com.qnnect.comments.dtos;

import com.qnnect.comments.domain.Comment;
import com.qnnect.user.domain.User;
import com.qnnect.user.dtos.ProfileResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class CommentResponse {
    Long commentId;
    LocalDate createdAt;
    ProfileResponse profileResponse;
    String content;
    String imageUrl1;
    String imageUrl2;
    String imageUrl3;
    String imageUrl4;
    String imageUrl5;
    int replyCount;

    public static CommentResponse from(Comment comment) {
        if(comment == null){
            return null;
        }
        return CommentResponse.builder()
                .commentId(comment.getId())
                .createdAt(comment.getCreatedAt().toLocalDate())
                .profileResponse(ProfileResponse.from(comment.getUser()))
                .content(comment.getContent())
                .imageUrl1(comment.getImageUrl1())
                .imageUrl2(comment.getImageUrl2())
                .imageUrl3(comment.getImageUrl3())
                .imageUrl4(comment.getImageUrl4())
                .imageUrl5(comment.getImageUrl5())
                .replyCount(comment.countReply())
                .build();
    }

    public static List<CommentResponse> listFrom(List<Comment> commentList, User user, List<Long> reportId) {
        if(commentList == null){
            return null;
        }
        return commentList.stream()
                .filter(comment -> !comment.getUser().getId().equals(user.getId()))
                .filter(comment -> !reportId.contains(comment.getUser().getReportId()))
                .map(CommentResponse::from)
                .collect(Collectors.toList());
    }
}
