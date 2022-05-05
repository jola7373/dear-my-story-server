package com.qnnect.comments.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CommentUpdateRequest {

    MultipartFile image1;
    MultipartFile image2;
    MultipartFile image3;
    MultipartFile image4;
    MultipartFile image5;

    boolean imageEmpty[];

    @Builder
    public CommentUpdateRequest(MultipartFile image1, MultipartFile image2, MultipartFile image3,
                                MultipartFile image4, MultipartFile image5, boolean[] imageEmpty) {
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
        this.image5 = image5;
        this.imageEmpty = imageEmpty;
    }
}
