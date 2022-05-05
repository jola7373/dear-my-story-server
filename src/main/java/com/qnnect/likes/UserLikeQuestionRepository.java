package com.qnnect.likes;

import com.qnnect.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserLikeQuestionRepository extends JpaRepository<UserLikeQuestion, Long> {
    void deleteByUser_IdAndQuestion_Id(@Param(value = "userId") UUID userId
            , @Param(value = "questionId") Long questionId);


    boolean existsByUser_IdAndQuestion_Id(@Param(value = "userId")UUID userId,
                                          @Param(value = "questionId") long questionId);
}
