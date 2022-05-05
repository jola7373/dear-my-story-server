package com.qnnect.comments.service;

import com.qnnect.comments.dtos.CommentDetailResponse;
import com.qnnect.comments.dtos.CommentUpdateRequest;
import com.qnnect.user.domain.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CommentService {
    Long create(Long cafeQuestionId, User user, String content, MultipartFile image1,
                MultipartFile image2, MultipartFile image3, MultipartFile imge4,
                MultipartFile image5);

    void deleteComment(Long commentId);

    CommentDetailResponse getComment(Long commentId, User user);

    void update(Long commentId, User user, String content, MultipartFile image1,
                MultipartFile image2, MultipartFile image3, MultipartFile image4,
                MultipartFile image5);

    public void update(Long commentId, String content);

    public void update(Long commentId, MultipartFile image1, MultipartFile image2, MultipartFile image3
            , MultipartFile image4, MultipartFile image5, String isImageEmpty1, String isImageEmpty2
            , String isImageEmpty3,String isImageEmpty4, String isImageEmpty5);
}
