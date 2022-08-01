package dev.yoon.gridgetest.domain.user.application.login.kakao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakaoClient", url = "https://kapi.kakao.com")
public interface KakaoFeignClient {

    @GetMapping(value = "/v2/user/me", consumes = "application/json")
    KakaoUserInfo getKakaoUserInfo(
            @RequestHeader("Content-type") String contentType,
            @RequestHeader("Authorization") String authorization
    );

}
