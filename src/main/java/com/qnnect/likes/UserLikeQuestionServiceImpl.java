package com.qnnect.likes;

import com.qnnect.common.exception.CustomException;
import com.qnnect.questions.domain.CafeQuestion;
import com.qnnect.questions.domain.Question;
import com.qnnect.questions.repository.CafeQuestionRepository;
import com.qnnect.user.domain.User;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.qnnect.common.exception.ErrorCode.CAFE_QUESTION_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserLikeQuestionServiceImpl implements UserLikeQuestionService{


    private final UserLikeQuestionRepository userLikeQuestionRepository;
    private final CafeQuestionRepository cafeQuestionRepository;

    @Transactional
    public boolean likeQuestion(boolean currentStatus, User user, Long cafeQuestionId){
        CafeQuestion cafeQuestion = cafeQuestionRepository.findById(cafeQuestionId)
                .orElseThrow(() -> new CustomException(CAFE_QUESTION_NOT_FOUND));

        Question question = cafeQuestion.getQuestions();

        if(currentStatus){
            userLikeQuestionRepository.deleteByUser_IdAndQuestion_Id(user.getId(), cafeQuestionId);
            return false;
        }else{
            UserLikeQuestion userLikeQuestion = UserLikeQuestion.builder().user(user).question(question).build();
            userLikeQuestionRepository.save(userLikeQuestion);
            return true;
        }
    }
}
