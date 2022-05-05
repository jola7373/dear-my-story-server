package com.qnnect.scrap.service;


import com.qnnect.questions.dto.QuestionResponse;
import com.qnnect.user.domain.User;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ScrapService {

    public void addScrap(User user, Long cafeQuestionId);

    public void deleteScrap(User user, Long cafeQuestionId);

    public List<QuestionResponse> getAllScraps(Pageable pageable, User user);

    public List<QuestionResponse> getCafeScraps(Pageable pageable, User user, Long cafeId);

    public List<QuestionResponse> searchScraps(Pageable pageable, User user, String searchWord );
}