package com.qnnect.comments.repository;

import com.qnnect.comments.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByCafeQuestion_Id(@Param(value="cafeQuestionId") long cafeQuestionId);
    Comment findByUser_IdAndCafeQuestion_Id(@Param(value = "userId")UUID userId,
                                            @Param(value = "cafeQuestionId")long cafeQuestionId);
}
