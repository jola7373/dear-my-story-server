package com.qnnect.notification.dto;

import com.qnnect.notification.domain.ENotificationType;
import com.qnnect.notification.domain.Notification;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class NotificationResponse {

    private long notificationId;

    private ENotificationType notificationType;

    private String content;

    private long contentId;

    private String groupName;

    private LocalDate createdAt;

    private boolean userRead;

    public static NotificationResponse from(Notification notification){
        String name = "";
        String content;

        if(notification.getNotificationType() == ENotificationType.comment){
            name = notification.getSenderName();
            content = "내 질문에 " + name + "님이 답글을 남겼습니다.";
        }else if (notification.getNotificationType() == ENotificationType.question){
            content = "질문이 도착했습니다: ";
        }else{
            name = notification.getSenderName();
            content = "내 질문에 " + name + "님이 댓글을 남겼습니다.";
        }
        content += notification.getContent();

        return NotificationResponse.builder().notificationType(notification.getNotificationType())
                .content(content).groupName(notification.getGroupName())
                .createdAt(notification.getCreatedAt().toLocalDate())
                .userRead(notification.isUserChecked())
                .contentId(notification.getContentId())
                .notificationId(notification.getId()).build();
    }
    public static List<NotificationResponse> listFrom(List<Notification> notificationList){
        return notificationList.stream().map(NotificationResponse::from)
                .collect(Collectors.toList());
    }
}
