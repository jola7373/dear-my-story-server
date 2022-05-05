package com.qnnect.comments.service;

import com.qnnect.comments.domain.Reply;
import com.qnnect.comments.dtos.ContentDto;
import com.qnnect.user.domain.User;

public interface ReplyService {
    public Reply createReply(Long commentId, String content, User user);
    public void deleteReply(Long replyId, User user);
    public void updateReply(Long replyId, String content, User user);
}
