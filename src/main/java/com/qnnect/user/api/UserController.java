package com.qnnect.user.api;

import com.qnnect.cafe.dto.CafeScrapResponse;
import com.qnnect.common.CurrentUser;
import com.qnnect.drink.dtos.StampResponse;
import com.qnnect.drink.service.DrinkService;
import com.qnnect.questions.dto.MyQuestionResponse;
import com.qnnect.user.dtos.MainResponse;
import com.qnnect.user.dtos.ReportResponse;
import com.qnnect.user.service.UserService;
import com.qnnect.user.domain.User;
import com.qnnect.user.dtos.ProfileResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Api(tags = {"사용자 API 정보를 제공"})
public class UserController {
    private final UserService userService;
    private final DrinkService drinkService;

    @ApiOperation(value = "프로필 설정")
    @PatchMapping(path = "/user/profile")
    public ResponseEntity<ProfileResponse> updateProfile(@ApiIgnore @CurrentUser User user, @RequestPart(required = false) MultipartFile profilePicture,
                                                         @RequestPart(required = false) String nickName) {
        ProfileResponse profileResponse = userService.updateUserProfile(user, nickName, profilePicture);
        return ResponseEntity.ok(profileResponse);
    }

    @ApiOperation(value = "사진 초기화")
    @PatchMapping(path = "/user/profile/default_image")
    public ResponseEntity<Void> updateProfilePicture(@ApiIgnore @CurrentUser User user) {
        userService.updateToDefaultImage(user);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "사용자 정보")
    @GetMapping("/user")
    public ResponseEntity<ProfileResponse> getUser(@ApiIgnore @CurrentUser User user) {
        return ResponseEntity.ok(ProfileResponse.from(user));
    }

    @ApiOperation(value = "알림 설정")
    @PatchMapping("/user/enablenotification")
    public ResponseEntity<Void> enableNotification(@ApiIgnore @CurrentUser User user, boolean enableNotification) {
        userService.enableNotification(user, enableNotification);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "알림 설정 여부 확인 api")
    @GetMapping("/user/enablenotification")
    public boolean isEnableNotification(@ApiIgnore @CurrentUser User user) {
        return user.isPushEnabled();
    }

    @ApiOperation(value = "메인 화면")
    @GetMapping("/home")
    public ResponseEntity<MainResponse> userMain (@ApiIgnore @CurrentUser User user) {
        MainResponse mainResponse = userService.getMain(user);
        return ResponseEntity.ok(mainResponse);
    }

    @ApiOperation(value = "사용자 카페 리스트")
    @GetMapping("/scrap/cafes")
    public ResponseEntity<List<CafeScrapResponse>> getUserCafeList (@ApiIgnore @CurrentUser User user) {
        List<CafeScrapResponse> cafeList = userService.getCafeList(user);
        return ResponseEntity.ok(cafeList);
    }

    @ApiOperation(value = "사용자 질문 리스트 전체")
    @GetMapping("/user/question/all")
    public ResponseEntity<List<MyQuestionResponse>> getAllUserQuestionList (@PageableDefault(sort="id", direction = Sort.Direction.DESC)final Pageable pageable, @ApiIgnore @CurrentUser User user) {
        List<MyQuestionResponse> questionResponse = userService.getQuestionAllList(user, pageable);
        return ResponseEntity.ok(questionResponse);
    }

    @ApiOperation(value = "사용자 질문 리스트 그룹별로")
    @GetMapping("/user/question/{cafeId}")
    public ResponseEntity<List<MyQuestionResponse>> getUserQuestionListByGroup (@PageableDefault(sort="id", direction = Sort.Direction.DESC)final Pageable pageable, @ApiIgnore @CurrentUser User user, @PathVariable long cafeId) {
        List<MyQuestionResponse> questionResponse = userService.getQuestionListByGroup(user, pageable, cafeId);
        return ResponseEntity.ok(questionResponse);
    }

    @ApiOperation(value = "사용자 신고하기")
    @PostMapping("/user/report")
    public ResponseEntity<Void> reportUser (long reportId, @ApiIgnore @CurrentUser User user) {
        userService.reportUser(reportId, user);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "사용자 신고해제")
    @DeleteMapping("/user/report")
    public ResponseEntity<Void> unreportUser (long reportId, @ApiIgnore @CurrentUser User user) {
        userService.unReportUser(reportId, user);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "사용자 신고리스트")
    @GetMapping("/user/report")
    public ResponseEntity<List<ReportResponse>> getReportUser (@ApiIgnore @CurrentUser User user) {
        return ResponseEntity.ok(userService.getReportUser(user));
    }


    @ApiOperation(value = "내 음료 스탬프")
    @GetMapping("/user/stamp")
    public ResponseEntity<List<StampResponse>> getStampUser (@ApiIgnore @CurrentUser User user) {
        return ResponseEntity.ok(drinkService.getStampUser(user));
    }

//    @ApiOperation(value = "사용자 질문 리스트")
//    @GetMapping("/user/question/")
//    public ResponseEntity<List<CafeScrapResponse>> getAllUserQuestionList (@ApiIgnore @CurrentUser User user) {
//        List<CafeScrapResponse> cafeList = userService.getCafeList(user);
//        return ResponseEntity.ok(cafeList);
//    }
}
