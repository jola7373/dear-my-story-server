package com.qnnect.questions.service;

import com.qnnect.cafe.domain.Cafe;
import com.qnnect.cafe.dto.OneCafeQuestionResponse;
import com.qnnect.questions.domain.Question;
import com.qnnect.questions.dto.CafeQuestionResponse;
import com.qnnect.questions.dto.QuestionDetailResponse;
import com.qnnect.questions.dto.QuestionRequest;
import com.qnnect.user.domain.User;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface CafeQuestionService {
    public Question findQuestionToday(Cafe cafe);

    Long create(Long cafeId, String content, User user);
    void update(Long questionId, String content);
    void delete(Long questionId);
    QuestionDetailResponse getQuestion(Long cafeQuestionId, User user);
    CafeQuestionResponse getCafeQuestions(Long cafeId, Pageable pageable);
    CafeQuestionResponse searchCafeQuestions(Long cafeId, String word,Pageable pageable);
    public void sendCafeQuestions(List<Cafe> filteredCafe);
    public void updateWaiting(Long questionId, QuestionRequest questionRequest);
    public void deleteWaiting(Long questionId);
    public OneCafeQuestionResponse getOneQuestion(Long cafeQuestionId, User user);
}
