package com.qnnect.scrap.service;

import com.qnnect.common.exception.CustomException;
import com.qnnect.common.exception.ErrorCode;
import com.qnnect.questions.domain.CafeQuestion;
import com.qnnect.questions.repository.CafeQuestionRepository;
import com.qnnect.scrap.domain.Scrap;
import com.qnnect.questions.dto.QuestionResponse;
import com.qnnect.scrap.repository.ScrapRepository;
import com.qnnect.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class ScrapServiceImpl implements ScrapService {

    private final ScrapRepository scrapRepository;
    private final CafeQuestionRepository cafeQuestionRepository;

    @Override
    public void addScrap(User user, Long cafeQuestionId) {
        CafeQuestion cafeQuestion = cafeQuestionRepository.getById(cafeQuestionId);
        scrapRepository.save(Scrap.builder()
                .user(user).cafeQuestion(cafeQuestion).build());
    }

    @Override
    public void deleteScrap(User user, Long cafeQuestionId) {
        try {

            Scrap scrap = scrapRepository.findByUser_IdAndCafeQuestion_Id(user.getId(), cafeQuestionId);
            log.info("deleting scrap");
            scrapRepository.delete(scrap);
            log.info("deleted scrap");
        } catch (CustomException e) {
            throw new CustomException(ErrorCode.QUESTION_NOT_SCRAPPED);
        }
    }

    @Override
    @Transactional
    public List<QuestionResponse> getAllScraps(Pageable pageable, User user){

        List<QuestionResponse> scrapList = scrapRepository.findByUser_Id(user.getId(),pageable)
                .stream()
                .map(QuestionResponse::from)
                .collect(Collectors.toList());
        return scrapList;
    }

    @Override
    public List<QuestionResponse> getCafeScraps(Pageable pageable, User user, Long cafeId ){

        List<QuestionResponse> scrapList = scrapRepository.findByUser_IdAndCafe_Id(user.getId(), cafeId ,pageable)
                .stream()
                .map(QuestionResponse::from)
                .collect(Collectors.toList());
        return scrapList;
    }

    @Override
    public List<QuestionResponse> searchScraps(Pageable pageable, User user, String searchWord){

        List<QuestionResponse> scrapList = scrapRepository.findByUser_IdAndWord(user.getId(), searchWord ,pageable)
                .stream()
                .map(QuestionResponse::from)
                .collect(Collectors.toList());
        return scrapList;
    }


}
