package com.qnnect.user.dtos;

import com.qnnect.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class ProfileResponse {

    private String profileImage;

    private String nickName;

    private int point;

    private long reportId;

    public static ProfileResponse from(User user){
        return new ProfileResponse(user.getProfilePicture(),user.getNickName(), user.getPoint(),
                user.getReportId());
    }
}
