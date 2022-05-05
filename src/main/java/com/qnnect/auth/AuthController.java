package com.qnnect.auth;

import com.qnnect.auth.dto.AuthRequest;
import com.qnnect.auth.dto.AuthResponse;
import com.qnnect.auth.dto.TokenReissue;
import com.qnnect.common.CurrentUser;
import com.qnnect.questions.dto.QuestionResponse;
import com.qnnect.user.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
@Api(tags = {"인증 관련 API"})
public class AuthController {

    private final AuthUserService authUserService;

    @ApiOperation(value = "로그인 및 회원가입 api")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> getTokens(@RequestBody AuthRequest authRequest){
        AuthResponse authResponse = authUserService.signUpOrLogIn(authRequest);
        return ResponseEntity.ok(authResponse);
    }

    @ApiOperation(value= "토큰 재발급")
    @PostMapping("/reissue")
    public ResponseEntity<TokenReissue> reissue(@RequestBody TokenReissue tokenReissueRequest) {
        TokenReissue tokenReissueResponse = authUserService.reissue(tokenReissueRequest);
        return ResponseEntity.ok(tokenReissueResponse);
    }

    @ApiOperation(value = "로그아웃")
    @PatchMapping("/logout")
    public ResponseEntity<Void> logOut (@ApiIgnore @CurrentUser User user) {
        authUserService.logout(user);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "회원탈퇴")
    @PatchMapping("/withdrawl")
    public ResponseEntity<Void> withdrawl (@ApiIgnore @CurrentUser User user) {
        authUserService.withdrawl(user);
        return ResponseEntity.ok().build();
    }
}
