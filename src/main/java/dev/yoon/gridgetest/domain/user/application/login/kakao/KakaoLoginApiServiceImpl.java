package dev.yoon.gridgetest.domain.user.application.login.kakao;

import dev.yoon.gridgetest.global.jwt.constant.GrantType;
import dev.yoon.gridgetest.domain.user.application.login.SocialLoginApiService;
import dev.yoon.gridgetest.domain.user.dto.login.OAuthAttributes;
import dev.yoon.gridgetest.domain.user.model.UserType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KakaoLoginApiServiceImpl implements SocialLoginApiService {

    private final KakaoFeignClient kakaoFeignClient;
    private final String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=utf-8";

    @Override
    public OAuthAttributes getUserInfo(String accessToken) {

        KakaoUserInfo kakaoUserInfo  = kakaoFeignClient.getKakaoUserInfo(CONTENT_TYPE, GrantType.BEARRER.getType() + " " + accessToken);
//        String email = kakaoUserInfo.getKakaoAccount().getEmail();
//        String nickname = kakaoUserInfo.getKakaoAccount().getProfile().getNickname(); // 실시간 프로필에 있는 닉네임 조회

        return OAuthAttributes.builder()
                .socialId(kakaoUserInfo.getId())
                .userType(UserType.KAKAO)
                .build();

    }



}
