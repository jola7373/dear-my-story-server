package com.qnnect.user.repositories;

import com.qnnect.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findBySocialId(String socialId);
    User findByReportId(long reportId);
}
