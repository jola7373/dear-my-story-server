package com.qnnect.auth.dto;


import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@ApiModel(value = "인증 후 응답")
public class AuthResponse {

    private String accessToken;
    private String refreshToken;
    private Boolean isNewMember;
    private Boolean userSettingDone;

}
