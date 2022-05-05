package com.qnnect.questions.dto;

import com.qnnect.cafe.dto.OneCafeQuestionResponse;
import com.qnnect.comments.domain.Comment;
import com.qnnect.comments.dtos.CommentResponse;
import com.qnnect.questions.domain.CafeQuestion;
import com.qnnect.user.domain.User;
import io.swagger.annotations.ApiModel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@ApiModel(value="질문리스트 댓글과 함께")
@Getter
public class QuestionDetailResponse {

    private OneCafeQuestionResponse questionMainResponse;

    private boolean isLiked;

    private boolean isScraped;

    private CommentResponse currentUserComment;

    private List<CommentResponse> comments = new ArrayList<>();

    public QuestionDetailResponse(CafeQuestion cafeQuestion, List<Comment> comments, User user, boolean isScraped,
                                  boolean isLiked, Comment currentUserComment, List<Long> reportId){
        this.questionMainResponse = OneCafeQuestionResponse.from(cafeQuestion, user);
        this.isLiked = isLiked;
        this.isScraped = isScraped;
        this.currentUserComment = CommentResponse.from(currentUserComment);
        this.comments = CommentResponse.listFrom(comments, user, reportId);
    }
}
