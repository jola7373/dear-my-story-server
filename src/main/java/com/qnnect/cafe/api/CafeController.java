package com.qnnect.cafe.api;

import com.qnnect.cafe.domain.Cafe;
import com.qnnect.cafe.dto.CafeRequest;
import com.qnnect.cafe.dto.CafeDetailResponse;
import com.qnnect.cafe.service.CafeService;
import com.qnnect.common.CurrentUser;
import com.qnnect.user.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Api(tags = {"카페 API"})
public class CafeController {

    private final CafeService cafeService;

    @PostMapping("/cafes")
    @ApiOperation(value = "카페 생성 api")
    public ResponseEntity<Long> createCafe(@RequestBody CafeRequest cafeCreateRequest,
                                                         @ApiIgnore @CurrentUser User user){
        Cafe cafe = cafeService.createCafe(cafeCreateRequest, user);
        return ResponseEntity.ok(cafe.getId());
    }

    @PostMapping("/cafes/join")
    @ApiOperation(value = "카페 참여 api")
    public ResponseEntity<CafeDetailResponse> joinCafe(@RequestParam String cafeCode,
                                                       @ApiIgnore @CurrentUser User user){
        CafeDetailResponse cafeResponse = cafeService.joinCafe(cafeCode, user);
        return ResponseEntity.ok(cafeResponse);
    }

    @GetMapping("/cafes/{cafeId}")
    @ApiOperation(value = "카페 홈 api")
    public ResponseEntity<CafeDetailResponse> showCafe(@PathVariable long cafeId,
                                                       @ApiIgnore @CurrentUser User user){
        CafeDetailResponse cafeResponse = cafeService.getCafe(cafeId, user);
        return ResponseEntity.ok(cafeResponse);
    }

    @ApiOperation(value = "카페 업데이트 api")
    @PatchMapping("/cafes/{cafeId}")
    public ResponseEntity<Void> updateCafe(@PathVariable Long cafeId,
                                           @RequestBody CafeRequest cafeupdateRequest,
                                           @ApiIgnore @CurrentUser User user){
        cafeService.updateCafe(cafeId, cafeupdateRequest, user);
        return ResponseEntity.ok().build();
    }


    @ApiOperation(value = "카페 삭제 api")
    @DeleteMapping("/cafes/{cafeId}")
    public ResponseEntity<Void> deleteDiary(@PathVariable Long cafeId, @ApiIgnore @CurrentUser User user){
        cafeService.deleteCafe(cafeId, user);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "카페 나가기 api")
    @PatchMapping("/cafes/leaver")
    public ResponseEntity<Void> leaveCafe(@RequestParam Long cafeId, @ApiIgnore @CurrentUser User user){
        cafeService.leaveCafe(cafeId, user);
        return ResponseEntity.noContent().build();
    }

}
