package com.qnnect.questions.repository;

import com.qnnect.questions.domain.EQuestionType;
import com.qnnect.questions.domain.EQuestionerType;
import com.qnnect.questions.domain.Question;
import com.qnnect.scrap.domain.Scrap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByUser_Id(@Param(value="userId") UUID userId, Pageable pageable);

    @Query(value="SELECT *  FROM question where question_type=:questionType AND questioner_type = 'admin' ORDER BY rand() LIMIT 1"
            ,nativeQuery=true)
    Question findByQuestionTypeRand(@Param(value = "questionType") String questionType);
}
