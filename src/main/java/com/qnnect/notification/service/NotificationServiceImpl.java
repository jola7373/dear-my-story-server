package com.qnnect.notification.service;


import com.qnnect.common.exception.CustomException;
import com.qnnect.common.exception.ErrorCode;
import com.qnnect.notification.domain.FcmToken;
import com.qnnect.notification.domain.Notification;
import com.qnnect.notification.dto.NotificationResponse;
import com.qnnect.notification.repository.FcmTokenRepository;
import com.qnnect.notification.repository.NotificationRepository;
import com.qnnect.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{

    private final NotificationRepository notificationRepository;
    private final FcmTokenRepository fcmTokenRepository;

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getNotification(User user, Pageable pageable){
        List<Notification> notificationList = notificationRepository.findAllByUser_Id(user.getId(), pageable);
        return NotificationResponse.listFrom(notificationList);
    }

    @Override
    public void setNotificationRead(long notificationId){
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(()-> new CustomException(ErrorCode.NOTIFICATION_NOT_FOUND));
        notification.setUserChecked(true);
        notificationRepository.save(notification);
    }

    public void saveToken(User user, String token){
        if(fcmTokenRepository.existsByUserId(user.getId())){
            FcmToken fcmToken = fcmTokenRepository.findByUserId(user.getId()).orElseThrow(()-> new CustomException(ErrorCode.FCM_TOKEN_NOT_FOUND));
            fcmToken.setToken(token);
            fcmTokenRepository.save(fcmToken);
        }else{
            fcmTokenRepository.save(FcmToken.builder().userId(user.getId())
                    .token(token).build());
        }
    }
}
