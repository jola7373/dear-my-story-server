package com.qnnect.comments.repository;

import com.qnnect.comments.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByComment_Id(@Param(value="commentId") long commentId);
}
