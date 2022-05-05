package com.qnnect.cafe.service;

import com.qnnect.cafe.domain.Cafe;
import com.qnnect.cafe.domain.CafeUser;
import com.qnnect.cafe.dto.CafeRequest;
import com.qnnect.cafe.dto.CafeDetailResponse;
import com.qnnect.cafe.repository.CafeRepository;
import com.qnnect.cafe.repository.CafeUserRepository;
import com.qnnect.common.exception.CustomException;
import com.qnnect.common.exception.ErrorCode;
import com.qnnect.questions.domain.CafeQuestion;
import com.qnnect.questions.domain.EQuestionType;
import com.qnnect.questions.domain.Question;
import com.qnnect.questions.repository.CafeQuestionRepository;
import com.qnnect.questions.repository.QuestionRepository;
import com.qnnect.user.domain.Report;
import com.qnnect.user.domain.User;
import com.qnnect.user.repositories.ReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class CafeServiceImpl implements CafeService {

    private final CafeRepository cafeRepository;
    private final CafeUserRepository cafeUserRepository;
    private final ReportRepository reportRepository;
    private final CafeQuestionRepository cafeQuestionRepository;
    private final QuestionRepository questionRepository;

    @Transactional
    public Cafe createCafe(CafeRequest cafeRequest, User user){
        Cafe cafe = cafeRequest.toEntity();
        log.info("created cafe");
        cafeRepository.save(cafe);
        CafeUser cafeUser = cafeUserRepository.save(CafeUser.builder().cafe(cafe).user(user).build());
        log.info("added user to cafe user");
        Question question = questionRepository.findByQuestionTypeRand(EQuestionType.공통.toString());
        cafeQuestionRepository.save(CafeQuestion.builder().cafe(cafe).question(question).build());

        return cafe;
    }

    @Transactional
    public CafeDetailResponse joinCafe(String code, User user){
        Cafe cafe = cafeRepository.findByCode(code).orElseThrow(()-> new CustomException(ErrorCode.INCORRECT_CAFE_CODE_EXCEPTION));
        if(cafeUserRepository.findByCafe_IdAndUser_Id(cafe.getId(), user.getId()) == null){
            long memberNum = cafeUserRepository.countByCafe_Id(cafe.getId());
            if(memberNum >= 5){
                throw new CustomException(ErrorCode.CAFE_MEMBER_EXCEED_EXCEPTION);
            }
            System.out.println(memberNum);
            cafeUserRepository.save(CafeUser.builder().cafe(cafe).user(user).build());
        }

        CafeUser currentCafeUser = cafeUserRepository.findByCafe_IdAndUser_Id(cafe.getId(), user.getId());
        List<Report> report = reportRepository.findAllByUserId(user.getId());
        List<Long> reportedUser = report.stream().map(Report::getReportedId).collect(Collectors.toList());
        return new CafeDetailResponse(cafe, currentCafeUser,user, reportedUser);
    }

    @Transactional(readOnly=true)
    public CafeDetailResponse getCafe(Long cafeId, User user){
        CafeUser currentCafeUser = cafeUserRepository.findByCafe_IdAndUser_Id(cafeId, user.getId());
        Cafe cafe = cafeRepository.getById(cafeId);
        List<Report> report = reportRepository.findAllByUserId(user.getId());
        List<Long> reportedUser = report.stream().map(Report::getReportedId).collect(Collectors.toList());
        return new CafeDetailResponse(cafe, currentCafeUser,user, reportedUser);
    }

    @Transactional
    @Override
    public void deleteCafe(Long cafeId, User user){
        cafeRepository.deleteById(cafeId);
    }

    @Transactional
    @Override
    public void leaveCafe(Long cafeId, User user){
        System.out.println("finding cafeUser");
        CafeUser cafeUser = cafeUserRepository.findByCafe_IdAndUser_Id(cafeId, user.getId());
        System.out.println("delete cafe user");
        cafeUserRepository.deleteById(cafeUser.getId());

        if(!cafeUserRepository.existsByCafe_Id(cafeId)){
            System.out.println("no cafe member");
            System.out.println("deleting cafe");
            cafeRepository.deleteById(cafeId);
        }
    }

    @Transactional
    @Override
    public Cafe updateCafe(Long cafeId, CafeRequest cafeCreateRequest, User user){
        Cafe cafe = cafeRepository.getById(cafeId);
        if(cafeCreateRequest.getDiaryColor() != null){
            cafe.setDiaryColor(cafeCreateRequest.getDiaryColor());
        }
        if(cafeCreateRequest.getGroupType() != null){
            cafe.setGroupType(cafeCreateRequest.getGroupType());
        }
        if(cafeCreateRequest.getQuestionCycle() != null){
            cafe.setQuestionCycle(cafeCreateRequest.getQuestionCycle());
        }
        if(cafeCreateRequest.getTitle() != null){
            cafe.setTitle(cafeCreateRequest.getTitle());
        }
        return cafe;
    }
}
