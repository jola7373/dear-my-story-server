package com.qnnect.scrap.repository;

import com.qnnect.scrap.domain.Scrap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    List<Scrap> findAllByUser_Id(@Param(value="userId") UUID userId);

    Scrap findByUser_IdAndCafeQuestion_Id(@Param(value = "userId") UUID userId
            , @Param(value = "cafeQuestionId") long cafeQuestionId);

    Page<Scrap> findByUser_Id(@Param(value="userId")UUID userId, Pageable pageable);

    @Query("SELECT m FROM Scrap m INNER JOIN m.cafeQuestion c WHERE m.user.id=:userId AND c.cafe.id=:cafeId")
    Page<Scrap> findByUser_IdAndCafe_Id(@Param(value="userId")UUID userId
            ,@Param(value = "cafeId") Long cafeId, Pageable pageable);

    @Query("SELECT m FROM Scrap m INNER JOIN m.cafeQuestion c INNER JOIN c.questions q WHERE m.user.id=:userId AND q.content LIKE %:word%")
    Page<Scrap> findByUser_IdAndWord(@Param(value="userId")UUID userId
            ,@Param(value = "word") String word, Pageable pageable);


    boolean existsByUser_IdAndCafeQuestion_Id(@Param(value = "userId") UUID userId,
                                              @Param(value = "cafeQuestionId") long cafeQuestionId);
}
