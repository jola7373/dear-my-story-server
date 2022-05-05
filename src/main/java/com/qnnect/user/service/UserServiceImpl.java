package com.qnnect.user.service;

import com.qnnect.cafe.domain.Cafe;
import com.qnnect.cafe.domain.CafeUser;
import com.qnnect.cafe.dto.CafeMainResponse;
import com.qnnect.cafe.dto.CafeScrapResponse;
import com.qnnect.cafe.repository.CafeUserRepository;
import com.qnnect.common.S3Uploader;
import com.qnnect.notification.repository.NotificationRepository;
import com.qnnect.questions.domain.CafeQuestion;
import com.qnnect.questions.domain.CafeQuestionWaitingList;
import com.qnnect.questions.domain.Question;
import com.qnnect.questions.dto.MyQuestionResponse;
import com.qnnect.questions.dto.QuestionResponse;
import com.qnnect.questions.repository.CafeQuestionRepository;
import com.qnnect.questions.repository.CafeQuestionWaitingListRespository;
import com.qnnect.questions.repository.QuestionRepository;
import com.qnnect.user.domain.Report;
import com.qnnect.user.domain.User;
import com.qnnect.user.dtos.MainResponse;
import com.qnnect.user.dtos.ProfileResponse;
import com.qnnect.user.dtos.ReportResponse;

import com.qnnect.user.repositories.ReportRepository;
import com.qnnect.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final S3Uploader s3Uploader;
    private final CafeUserRepository cafeUserRepository;
    private final CafeQuestionRepository cafeQuestionRepository;
    private final String defaultImage = "https://dev-qnnect-profile.s3.ap-northeast-2.amazonaws.com/profileDefault.svg";
    private final QuestionRepository questionRepository;
    private final ReportRepository reportRepository;
    private final CafeQuestionWaitingListRespository cafeQuestionWaitingListRespository;
    private final NotificationRepository notificationRepository;

    @Override
    public void enableNotification(User user, boolean enabledNotification) {
        if(user.isPushEnabled() != enabledNotification){
            user.setPushEnabled(enabledNotification);
        }
        userRepository.save(user);
    }

    @Transactional
    public ProfileResponse updateUserProfile(User user, String nickName, MultipartFile profileImage){
        if(!StringUtils.isEmpty(nickName)){
            user.setNickName(nickName);
            log.info("updated user nickname");
        }

        if(profileImage != null){
            if(user.getProfilePicture() != null && user.getProfilePicture() != defaultImage){
                s3Uploader.deleteS3(user.getProfilePicture(), "profile");
            }
            try {
                String imageUrl = s3Uploader.upload(profileImage, "profile");
                user.setProfilePicture(imageUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            log.info("updated profile image");
        }
        User currUser = userRepository.save(user);
        System.out.println(currUser.getCreatedAt());
        return ProfileResponse.from(user);
    }

    @Transactional
    public void updateToDefaultImage(User user){
        if(user.getProfilePicture() != defaultImage){
            s3Uploader.deleteS3(user.getProfilePicture(), "profile");
        }
        user.setProfilePicture(defaultImage);
        userRepository.save(user);
    }

    public MainResponse getMain(User user){

        List<CafeUser> cafeUserList = cafeUserRepository.findAllByUser_Id(user.getId());
        List<Cafe> cafeList = cafeUserList.stream().map(CafeUser::getCafe).collect(Collectors.toList());
        List<Long> cafeIdList = cafeList.stream().map(Cafe::getId).collect(Collectors.toList());
        List<CafeQuestion> todayQuestionList = getLatestQuestion(cafeIdList);
        boolean hasUnreadNotification = notificationRepository.existsByUserIdAndUserChecked(user.getId(), false);

        if(cafeUserList == null){
            return new MainResponse(user, null, null, hasUnreadNotification);
        }
        return new MainResponse(user, cafeList,todayQuestionList, hasUnreadNotification);
    }

    public List<CafeQuestion> getLatestQuestion(List<Long> cafeIdList){
        List<CafeQuestion> todayQuestionList = new ArrayList<>();
        for(Long cafeId:cafeIdList){
            todayQuestionList.add(cafeQuestionRepository.
                    findTop1ByCafe_IdOrderByCreatedAtDesc(cafeId));
        }
        return todayQuestionList;
    }

    public List<CafeScrapResponse> getCafeList(User user){
        List<CafeUser> cafeUserList = cafeUserRepository.findAllByUser_Id(user.getId());
        List<Cafe> cafeList = cafeUserList.stream().map(CafeUser::getCafe).collect(Collectors.toList());
        return CafeScrapResponse.listFrom(cafeList);
    }

    @Override
    @Transactional
    public List<MyQuestionResponse> getQuestionAllList(User user, Pageable pageable){
        List<Question> questions = questionRepository.findByUser_Id(user.getId(),pageable);

        List<CafeQuestion> cafeQuestionList = questions.stream()
                .filter(question -> cafeQuestionRepository.existsByQuestions_Id(question.getId()))
                .map(question -> cafeQuestionRepository.findByQuestions_Id(question.getId()))
                .collect(Collectors.toList());

        List<CafeQuestionWaitingList> cafeQuestionWaitingLists = questions.stream()
                .filter(question -> cafeQuestionWaitingListRespository.existsByQuestion_Id(question.getId()))
                .map(question -> cafeQuestionWaitingListRespository.findByQuestion_Id(question.getId()))
                .collect(Collectors.toList());

        return MyQuestionResponse.listFromAllQuestions(cafeQuestionList, cafeQuestionWaitingLists);
    }

    @Override
    @Transactional
    public List<MyQuestionResponse> getQuestionListByGroup(User user, Pageable pageable, long cafeId){
        List<Question> questions = questionRepository.findByUser_Id(user.getId(),pageable);

        List<CafeQuestion> cafeQuestionList = questions.stream()
                .filter(question -> cafeQuestionRepository.existsByQuestions_Id(question.getId()))
                .map(question -> cafeQuestionRepository.findByQuestions_IdAndCafe_Id(question.getId(), cafeId))
                .collect(Collectors.toList());

        List<CafeQuestionWaitingList> cafeQuestionWaitingLists = questions.stream()
                .filter(question -> cafeQuestionWaitingListRespository.existsByQuestion_Id(question.getId()))
                .map(question -> cafeQuestionWaitingListRespository.findByQuestion_IdAndCafe_Id(question.getId(),cafeId))
                .collect(Collectors.toList());

        return MyQuestionResponse.listFromAllQuestions(cafeQuestionList, cafeQuestionWaitingLists);
    }

    @Override
    @Transactional
    public void reportUser(long userId, User user){
        Report report = Report.builder().user_id(user.getId()).reported_id(userId).build();
        reportRepository.save(report);
    }

    @Override
    @Transactional
    public void unReportUser(long userId, User user){
        Report report = reportRepository.findByUserIdAndReportedId(user.getId(), userId);
        reportRepository.delete(report);
    }

    @Override
    @Transactional
    public List<ReportResponse> getReportUser(User user){
        List<Report> reports = reportRepository.findAllByUserId(user.getId());
        List<User> reportedUsers = reports.stream().map(Report::getReportedId)
                .map(id-> userRepository.findByReportId(id)).collect(Collectors.toList());
        return ReportResponse.listFrom(reportedUsers);
    }


}

