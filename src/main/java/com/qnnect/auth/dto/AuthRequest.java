package com.qnnect.auth.dto;

import com.qnnect.auth.ELoginType;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value = "인증 요청")
public class AuthRequest {

    private String accessToken;
    private ELoginType loginType;

}
