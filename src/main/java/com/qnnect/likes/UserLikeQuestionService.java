package com.qnnect.likes;

import com.qnnect.user.domain.User;

public interface UserLikeQuestionService {
    public boolean likeQuestion(boolean currentStatus, User user, Long CafeQuestionId);
}
