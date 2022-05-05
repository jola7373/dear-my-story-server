package com.qnnect.questions.service;

import com.qnnect.cafe.domain.Cafe;
import com.qnnect.cafe.domain.CafeUser;
import com.qnnect.cafe.dto.OneCafeQuestionResponse;
import com.qnnect.cafe.repository.CafeRepository;
import com.qnnect.cafe.repository.CafeUserRepository;
import com.qnnect.comments.domain.Comment;
import com.qnnect.comments.repository.CommentRepository;
import com.qnnect.common.exception.CustomException;
import com.qnnect.common.exception.ErrorCode;
import com.qnnect.likes.UserLikeQuestion;
import com.qnnect.likes.UserLikeQuestionRepository;
import com.qnnect.notification.FirebaseCloudMessageService;
import com.qnnect.notification.domain.ENotificationType;
import com.qnnect.notification.domain.FcmToken;
import com.qnnect.notification.domain.Notification;
import com.qnnect.notification.repository.FcmTokenRepository;
import com.qnnect.notification.repository.NotificationRepository;
import com.qnnect.questions.domain.*;
import com.qnnect.questions.dto.CafeQuestionResponse;
import com.qnnect.questions.dto.QuestionDetailResponse;
import com.qnnect.questions.dto.QuestionRequest;
import com.qnnect.questions.dto.QuestionResponse;
import com.qnnect.questions.repository.CafeQuestionRepository;
import com.qnnect.questions.repository.CafeQuestionWaitingListRespository;
import com.qnnect.questions.repository.QuestionRepository;
import com.qnnect.scrap.repository.ScrapRepository;
import com.qnnect.user.domain.Report;
import com.qnnect.user.domain.User;
import com.qnnect.user.repositories.ReportRepository;
import com.qnnect.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class CafeQuestionServiceImpl implements CafeQuestionService {

    private final CafeQuestionRepository cafeQuestionRepository;
    private final QuestionRepository questionRepository;
    private final CafeRepository cafeRepository;
    private final CafeQuestionWaitingListRespository cafeQuestionWaitingListRespository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final ScrapRepository scrapRepository;
    private final UserLikeQuestionRepository likeRepository;
    private final ReportRepository reportRepository;
    private final NotificationRepository notificationRepository;
    private final CafeUserRepository cafeUserRepository;
    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final FcmTokenRepository fcmTokenRepository;

    @Override
    public Question findQuestionToday(Cafe cafe) {
        System.out.println("service");
        CafeQuestion cafeQuestion = cafeQuestionRepository.findTop1ByCafe_IdOrderByCreatedAtDesc(cafe.getId());
        System.out.println(cafeQuestion);
        return cafeQuestion.getQuestions();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(Long cafeId, String content, User user) {
        Cafe cafe = cafeRepository.getById(cafeId);
        cafe.getGroupType().toString();
        Question question = questionRepository.save(Question.builder().content(content)
                .questionType(cafe.getGroupType().toString()).user(user).build());

        cafeQuestionWaitingListRespository.save(CafeQuestionWaitingList.builder()
                .question(question).cafe(cafe).build());

        user.addPoint(10);
        userRepository.save(user);
        return question.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long cafeQuestionId, String content) {
        CafeQuestion cafeQuestion = cafeQuestionRepository.getById(cafeQuestionId);
        Question question = cafeQuestion.getQuestions();
        question.setContent(content);
        questionRepository.save(question);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateWaiting(Long questionId, QuestionRequest questionRequest) {
        Question question = questionRepository.getById(questionId);
        question.setContent(questionRequest.getContent());
        questionRepository.save(question);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long cafeQuestionId) {
        CafeQuestion cafeQuestion = cafeQuestionRepository.getById(cafeQuestionId);
        Question question = cafeQuestion.getQuestions();
        questionRepository.deleteById(question.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteWaiting(Long questionId) {
        Question question = questionRepository.getById(questionId);
        questionRepository.deleteById(question.getId());
    }


    @Transactional
    public QuestionDetailResponse getQuestion(Long cafeQuestionId, User user) {
        CafeQuestion cafeQuestion = cafeQuestionRepository.getById(cafeQuestionId);
        log.info("getting cafeQuestion");
        List<Comment> comments = commentRepository.findAllByCafeQuestion_Id(cafeQuestionId);
        boolean isScraped = scrapRepository.existsByUser_IdAndCafeQuestion_Id(user.getId()
                , cafeQuestion.getId());
        System.out.println(user.getNickName());
        System.out.println("isScraped" + isScraped);
        boolean isLiked = likeRepository.existsByUser_IdAndQuestion_Id(user.getId(),
                cafeQuestion.getQuestions().getId());
        Comment currentUserComment = commentRepository.findByUser_IdAndCafeQuestion_Id(user.getId(), cafeQuestionId);
        List<Report> report = reportRepository.findAllByUserId(user.getId());
        List<Long> reportedUser = report.stream().map(Report::getReportedId).collect(Collectors.toList());
        return new QuestionDetailResponse(cafeQuestion, comments, user, isScraped, isLiked, currentUserComment, reportedUser);
    }

    @Transactional
    public OneCafeQuestionResponse getOneQuestion(Long cafeQuestionId, User user) {
        CafeQuestion cafeQuestion = cafeQuestionRepository.getById(cafeQuestionId);
        return OneCafeQuestionResponse.from(cafeQuestion, user);
    }

    @Override
    @Transactional
    public CafeQuestionResponse getCafeQuestions(Long cafeId, Pageable pageable) {
        List<CafeQuestion> cafeQuestion = cafeQuestionRepository.findAllByCafe_Id(cafeId, pageable);
        return CafeQuestionResponse.from(cafeQuestion);
    }

    @Override
    @Transactional
    public CafeQuestionResponse searchCafeQuestions(Long cafeId, String word, Pageable pageable) {
        List<CafeQuestion> cafeQuestion = cafeQuestionRepository.findByCafe_IdAndWord(cafeId, word, pageable);

        return CafeQuestionResponse.from(cafeQuestion);
    }

    //여기서 질문을 보내는데
    //waiting list있냐 없냐에 분기 처리 & waiting list비워주기
    //없을 시에는 갯수 % 4가 0일때 만 공통이외에는 questionType별로
    //중복검사 후
    @Override
    @Transactional
    public void sendCafeQuestions(List<Cafe> filteredCafe) {
        for (int i = 0; i < filteredCafe.size(); i++) {
            if (cafeQuestionWaitingListRespository.existsByCafe_Id(filteredCafe.get(i).getId())) {
                List<CafeQuestionWaitingList> cafeQuestionWaitingLists = cafeQuestionWaitingListRespository
                        .findAllByCafe_Id(filteredCafe.get(i).getId());
                for (int j = 0; j < cafeQuestionWaitingLists.size(); j++) {
                    CafeQuestion cafeQuestion = CafeQuestion.builder().question(cafeQuestionWaitingLists.get(j).getQuestion())
                            .cafe(filteredCafe.get(i)).build();
                    cafeQuestionRepository.save(cafeQuestion);
                    sendCafeQuestionNotification(cafeQuestion);
                    cafeQuestionWaitingListRespository.delete(cafeQuestionWaitingLists.get(j));
                }

            } else {
                EQuestionType questionType = EQuestionType.공통;
                if (cafeQuestionRepository.countByCafe_Id(filteredCafe.get(i).getId()) % 4 != 0) {
                    questionType = EQuestionType.valueOf(filteredCafe.get(i).getGroupType().toString());
                }
                Question question = questionRepository.findByQuestionTypeRand(questionType.toString());
                while (cafeQuestionRepository.existsByCafe_IdAndQuestions_Id(filteredCafe.get(i).getId(), question.getId())) {
                    question = questionRepository.findByQuestionTypeRand(questionType.toString());
                }

                CafeQuestion cafeQuestion = cafeQuestionRepository.save(CafeQuestion.builder().cafe(filteredCafe.get(i)).question(question).build());
                sendCafeQuestionNotification(cafeQuestion);
            }
        }
        System.out.println("Done+++++++++++++");
    }

    public void sendCafeQuestionNotification(CafeQuestion cafeQuestion) {
        Cafe cafe = cafeQuestion.getCafe();
        List<CafeUser> cafeUserList = cafeUserRepository.findAllByCafe_Id(cafe.getId());

        System.out.println("cafeQuestionId=-==============" + cafeQuestion.getQuestions().getId());
        System.out.println("cafeQuestion========" + cafeQuestion.getQuestions().getContent());
        for (int i = 0; i < cafeUserList.size(); i++) {
            notificationRepository.save(Notification.builder()
                    .notificationType(ENotificationType.question)
                    .content(cafeQuestion.getQuestions().getContent())
                    .contentId(cafeQuestion.getId())
                    .user(cafeUserList.get(i).getUser())
                    .groupName(cafeQuestion.getCafe().getTitle())
                    .build());

            if(cafeUserList.get(i).getUser().isPushEnabled()){
                try{
                    FcmToken fcmToken = fcmTokenRepository.findByUserId(cafeUserList.get(i).getUser().getId())
                            .orElseThrow(()-> new CustomException(ErrorCode.INVALID_AUTH_TOKEN));
                    firebaseCloudMessageService.sendMessageTo(
                            fcmToken.getToken(),
                            "📮"+ cafe.getTitle() + "에 질문이 도착했어요! 답변을 달러 가볼까요?",
                            cafeQuestion.getQuestions().getContent());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
