package com.qnnect.notification.repository;

import com.qnnect.notification.domain.Notification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByUser_Id(@Param(value = "userId") UUID userId, Pageable pageable);
    boolean existsByUserIdAndUserChecked(@Param(value = "userId") UUID userID, @Param(value = "userChecked") boolean userChecked);
}
