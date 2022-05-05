package com.qnnect.version;

import com.qnnect.drink.dtos.DrinkResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Api(tags = {"버전 정보 확인 api"})
public class VersionController {

    private final VersionService versionService;

    @ApiOperation(value = "버전 정보 확인 api")
    @GetMapping("/version")
    public ResponseEntity<Boolean> checkVersionInfo(@RequestParam EOs osType, @RequestParam String currentVersion){
        return ResponseEntity.ok(versionService.checkIsVersionValid(osType, currentVersion));

    }
}
