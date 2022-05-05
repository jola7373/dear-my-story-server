package com.qnnect.comments.service;

import com.qnnect.comments.domain.Comment;
import com.qnnect.comments.domain.Reply;
import com.qnnect.comments.dtos.ContentDto;
import com.qnnect.comments.repository.CommentRepository;
import com.qnnect.comments.repository.ReplyRepository;
import com.qnnect.common.exception.CustomException;
import com.qnnect.common.exception.ErrorCode;
import com.qnnect.notification.FirebaseCloudMessageService;
import com.qnnect.notification.domain.ENotificationType;
import com.qnnect.notification.domain.FcmToken;
import com.qnnect.notification.domain.Notification;
import com.qnnect.notification.repository.FcmTokenRepository;
import com.qnnect.notification.repository.NotificationRepository;
import com.qnnect.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Repository
public class ReplyServiceImpl implements ReplyService{

    private final ReplyRepository replyRepository;
    private final CommentRepository commentRepository;
    private final NotificationRepository notificationRepository;
    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final FcmTokenRepository fcmTokenRepository;

    public Reply createReply(Long commentId, String content, User user){
        Comment comment = commentRepository.getById(commentId);
        Reply reply = replyRepository.save(Reply.builder().content(content).user(user)
                .comment(comment).build());
        sendReplyNotification(comment.getUser(), reply);
        return reply;
    }

    public void sendReplyNotification(User commentUser, Reply reply) {
        if (!commentUser.getId().equals(reply.getUser().getId())) {
            Notification notification = Notification.builder().notificationType(ENotificationType.reply)
                    .contentId(reply.getComment().getId()).user(commentUser).content(reply.getContent())
                    .senderName(reply.getUser().getNickName())
                    .groupName(reply.getComment().getCafeQuestion().getCafe().getTitle()).build();
            notificationRepository.save(notification);

            if(commentUser.isPushEnabled()){
                try{
                    FcmToken fcmToken = fcmTokenRepository.findByUserId(commentUser.getId())
                            .orElseThrow(()-> new CustomException(ErrorCode.INVALID_AUTH_TOKEN));
                    firebaseCloudMessageService.sendMessageTo(
                            fcmToken.getToken(),
                            "📮내 답변에 댓글이 달렸어요! 댓글을 보러 가볼까요?",
                            reply.getContent());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void updateReply(Long replyId, String content, User user){
        Reply reply = replyRepository.getById(replyId);
        if(content != null){
            reply.setContent(content);
        }
        replyRepository.save(reply);
    }

    public void deleteReply(Long replyId, User user){
        replyRepository.deleteById(replyId);
    }
}
