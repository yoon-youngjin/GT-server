package dev.yoon.gridgetest.domain.user.application.login;


import dev.yoon.gridgetest.domain.user.dto.login.OAuthAttributes;

public interface SocialLoginApiService {

    OAuthAttributes getUserInfo(String accessToken);
}
