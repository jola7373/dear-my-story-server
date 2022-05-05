package com.qnnect.notification.repository;

import com.qnnect.notification.domain.FcmToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {
    Optional<FcmToken> findByUserId(@Param(value = "userId") UUID userId);

    boolean existsByUserId(@Param(value = "userId") UUID userId);
}
