package com.qnnect.comments.service;

import com.qnnect.cafe.domain.CafeUser;
import com.qnnect.cafe.repository.CafeUserRepository;
import com.qnnect.comments.domain.Comment;
import com.qnnect.comments.domain.Reply;
import com.qnnect.comments.dtos.CommentDetailResponse;
import com.qnnect.comments.dtos.CommentUpdateRequest;
import com.qnnect.comments.repository.CommentRepository;
import com.qnnect.comments.repository.ReplyRepository;
import com.qnnect.common.S3Uploader;
import com.qnnect.common.exception.CustomException;
import com.qnnect.common.exception.ErrorCode;

import com.qnnect.notification.FirebaseCloudMessageService;
import com.qnnect.drink.domain.UserDrinkSelected;
import com.qnnect.notification.domain.ENotificationType;
import com.qnnect.notification.domain.FcmToken;
import com.qnnect.notification.domain.Notification;
import com.qnnect.notification.repository.FcmTokenRepository;
import com.qnnect.notification.repository.NotificationRepository;
import com.qnnect.questions.domain.CafeQuestion;
import com.qnnect.questions.domain.EQuestionerType;
import com.qnnect.questions.dto.CafeQuestionResponse;
import com.qnnect.questions.repository.CafeQuestionRepository;
import com.qnnect.user.domain.Report;
import com.qnnect.user.domain.User;
import com.qnnect.user.repositories.ReportRepository;
import com.qnnect.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.qnnect.common.exception.ErrorCode.CAFE_QUESTION_NOT_FOUND;
import static com.qnnect.common.exception.ErrorCode.COMMENT_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final S3Uploader s3Uploader;
    private final CafeQuestionRepository cafeQuestionRepository;
    private final UserRepository userRepository;
    private final ReplyRepository replyRepository;
    private final ReportRepository reportRepository;
    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final FcmTokenRepository fcmTokenRepository;
    private final NotificationRepository notificationRepository;
    private final CafeUserRepository cafeUserRepository;

    @Override
    @Transactional
    public Long create(Long cafeQuestionId, User user, String content, MultipartFile image1,
                       MultipartFile image2, MultipartFile image3, MultipartFile image4,
                       MultipartFile image5) {
        String[] image = new String[5];


        System.out.println(cafeQuestionId);
        CafeQuestion cafeQuestion = cafeQuestionRepository.findById(cafeQuestionId)
                .orElseThrow(() -> new CustomException(CAFE_QUESTION_NOT_FOUND));
        CafeUser cafeUser = cafeUserRepository.findByCafe_IdAndUser_Id(cafeQuestion.getCafe().getId(),user.getId());
        if(cafeUser.getUserDrinkSelected().size() == 0){
            throw new CustomException(ErrorCode.CAFE_DRINK_NOT_SELECTED_EXCEPTION);
        }
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(cafeQuestion.getCreatedAt().plusDays(7))) {
            throw new CustomException(ErrorCode.CAFE_QUESTION_DATE_PASSED);
        }

        image = imageUploader(image1, image2, image3, image4, image5, image);

        Comment comment = commentRepository.save(Comment.builder().content(content)
                .imageUrl1(image[0]).imageUrl2(image[1]).imageUrl3(image[2])
                .imageUrl4(image[3]).imageUrl5(image[4]).user(user)
                .cafeQuestion(cafeQuestion).build());

        user.addPoint(5);
        userRepository.save(user);

        if (cafeQuestion.getQuestions().getQuestionerType() == EQuestionerType.user) {
            sendCommentNotification(cafeQuestion.getQuestions().getUser(), comment);
        }
        return comment.getId();
    }

    public void sendCommentNotification(User questioner, Comment comment) {
        if(questioner.getId().equals(comment.getUser().getId())){
            Notification notification = Notification.builder().notificationType(ENotificationType.comment)
                    .contentId(comment.getId()).user(questioner).content(comment.getContent())
                    .senderName(comment.getUser().getNickName())
                    .groupName(comment.getCafeQuestion().getCafe().getTitle()).build();
            notificationRepository.save(notification);
        }
        if(questioner.isPushEnabled()){
            try{
                FcmToken fcmToken = fcmTokenRepository.findByUserId(questioner.getId())
                        .orElseThrow(()-> new CustomException(ErrorCode.INVALID_AUTH_TOKEN));
                firebaseCloudMessageService.sendMessageTo(
                        fcmToken.getToken(),
                        "📮내 질문에 답변이 달렸어요! 답변을 보러 가볼까요?",
                        comment.getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public String[] imageUploader(MultipartFile image1, MultipartFile image2,
                                  MultipartFile image3, MultipartFile image4,
                                  MultipartFile image5, String[] image) {
        System.out.println("imageUploader");
        if (image1 != null) {
            image[0] = uploadImage(image1);
        }
        if (image2 != null) {
            image[1] = uploadImage(image2);
        }
        if (image3 != null) {
            image[2] = uploadImage(image3);
        }
        if (image4 != null) {
            image[3] = uploadImage(image4);
        }
        if (image5 != null) {
            image[4] = uploadImage(image5);
        }
        return image;
    }

    @Transactional
    public String uploadImage(MultipartFile image) {
        try {
            String imageUrl = s3Uploader.upload(image, "comment");
            return imageUrl;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Transactional
    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(COMMENT_NOT_FOUND));
        imageDeleter(comment);
        commentRepository.deleteById(commentId);
    }

    public void imageDeleter(Comment comment) {
        if (comment.getImageUrl1() != null) {
            s3Uploader.deleteS3(comment.getImageUrl1(), "comment");
            comment.setImageUrl1(null);
        }
        if (comment.getImageUrl2() != null) {
            s3Uploader.deleteS3(comment.getImageUrl2(), "comment");
            comment.setImageUrl2(null);
        }
        if (comment.getImageUrl3() != null) {
            s3Uploader.deleteS3(comment.getImageUrl3(), "comment");
            comment.setImageUrl3(null);
        }
        if (comment.getImageUrl4() != null) {
            s3Uploader.deleteS3(comment.getImageUrl4(), "comment");
            comment.setImageUrl4(null);
        }
        if (comment.getImageUrl5() != null) {
            s3Uploader.deleteS3(comment.getImageUrl5(), "comment");
            comment.setImageUrl5(null);
        }
    }


    @Override
    @Transactional
    public CommentDetailResponse getComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(COMMENT_NOT_FOUND));
        List<Reply> reply = replyRepository.findAllByComment_Id(comment.getId());
        List<Report> report = reportRepository.findAllByUserId(user.getId());
        List<Long> reportedUser = report.stream().map(Report::getReportedId).collect(Collectors.toList());

        return CommentDetailResponse.from(comment, user, reply, reportedUser);
    }

    @Override
    @Transactional
    public void update(Long commentId, User user, String content, MultipartFile image1,
                       MultipartFile image2, MultipartFile image3, MultipartFile image4,
                       MultipartFile image5) {

        String imageUrl[] = new String[5];


        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(COMMENT_NOT_FOUND));

        comment.setContent(content);
        imageDeleter(comment);
        imageUrl = imageUploader(image1, image2, image3, image4, image5, imageUrl);
        comment.setImageUrl1(imageUrl[0]);
        comment.setImageUrl2(imageUrl[1]);
        comment.setImageUrl3(imageUrl[2]);
        comment.setImageUrl4(imageUrl[3]);
        comment.setImageUrl5(imageUrl[4]);

        commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void update(Long commentId, String content) {
        System.out.println("update content");
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(COMMENT_NOT_FOUND));
        if (content != null) {
            comment.setContent(content);
        }
        commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void update(Long commentId, MultipartFile image1, MultipartFile image2, MultipartFile image3
            , MultipartFile image4, MultipartFile image5, String isImageEmpty1, String isImageEmpty2
            , String isImageEmpty3,String isImageEmpty4, String isImageEmpty5) {
        String imageUrl[] = new String[5];
        System.out.println("update image");
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(COMMENT_NOT_FOUND));

        imageUrl = imageUploader(image1, image2, image3, image4, image5, imageUrl);

        if(imageUrl[0] != null){
            comment.setImageUrl1(imageUrl[0]);
        }
        if(imageUrl[1] != null){
            comment.setImageUrl2(imageUrl[1]);
        }
        if(imageUrl[2] != null){
            comment.setImageUrl3(imageUrl[2]);
        }
        if(imageUrl[3] != null){
            comment.setImageUrl4(imageUrl[3]);
        }
        if(imageUrl[4] != null){
            comment.setImageUrl5(imageUrl[4]);
        }

        if (isImageEmpty1.equals("1")) {
            s3Uploader.deleteS3(comment.getImageUrl1(), "comment");
            comment.setImageUrl1(null);
        }
        if (isImageEmpty2.equals("1")) {
            s3Uploader.deleteS3(comment.getImageUrl2(), "comment");
            comment.setImageUrl2(null);
        }
        if (isImageEmpty3.equals("1")) {
            s3Uploader.deleteS3(comment.getImageUrl3(), "comment");
            comment.setImageUrl3(null);
        }
        if (isImageEmpty4.equals("1")) {
            s3Uploader.deleteS3(comment.getImageUrl4(), "comment");
            comment.setImageUrl4(null);
        }
        if (isImageEmpty5.equals("1")) {
            s3Uploader.deleteS3(comment.getImageUrl5(), "comment");
            comment.setImageUrl5(null);
        }

        commentRepository.save(comment);
    }
}
