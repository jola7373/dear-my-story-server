package com.qnnect.comments.dtos;

import com.qnnect.comments.domain.Comment;
import com.qnnect.comments.domain.Reply;
import com.qnnect.user.domain.User;
import com.qnnect.user.dtos.ProfileResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class ReplyResponse {

    private Long replyId;

    private ProfileResponse writerInfo;

    private String content;

    private boolean writer;

    private LocalDate createdAt;


    public static ReplyResponse from(Reply reply, User user) {

        return ReplyResponse.builder()
                .replyId(reply.getId())
                .writerInfo(ProfileResponse.from(reply.getUser()))
                .content(reply.getContent())
                .createdAt(reply.getCreatedAt().toLocalDate())
                .writer(isWriter(reply, user))
                .build();
    }

    public static boolean isWriter(Reply reply,User user){
        System.out.println(reply.getUser().getId().equals(user.getId()));
        return reply.getUser().getId().equals(user.getId());
    }

    public static List<ReplyResponse> listFrom(List<Reply> replyList, User user,List<Long> reportedUser) {
        if(replyList == null){
            return null;
        }
        return replyList.stream()
                .filter(reply -> !reportedUser.contains(reply.getUser().getReportId()))
                .map(reply -> from(reply, user))
                .collect(Collectors.toList());
    }
}
