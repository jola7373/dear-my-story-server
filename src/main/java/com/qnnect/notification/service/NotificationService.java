package com.qnnect.notification.service;

import com.qnnect.notification.dto.NotificationResponse;
import com.qnnect.user.domain.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NotificationService {

    public List<NotificationResponse> getNotification(User user, Pageable pageable);
    public void setNotificationRead(long notificationId);
    public void saveToken(User user, String token);

}
