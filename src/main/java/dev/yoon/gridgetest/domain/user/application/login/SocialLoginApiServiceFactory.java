package dev.yoon.gridgetest.domain.user.application.login;

import dev.yoon.gridgetest.domain.user.model.UserType;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SocialLoginApiServiceFactory {

    private static Map<String, SocialLoginApiService> socialApiServices;

    public SocialLoginApiServiceFactory(Map<String, SocialLoginApiService> socialApiSerivces) {
        this.socialApiServices = socialApiSerivces;
    }

    public static SocialLoginApiService getSocialLoginApiService(UserType userType) {
        String socialApiServiceBeanName = "";

        if(UserType.KAKAO.equals(userType)) {
            socialApiServiceBeanName = "kakaoLoginApiServiceImpl";
        }

        SocialLoginApiService socialLoginApiSerivce = socialApiServices.get(socialApiServiceBeanName);
        return socialLoginApiSerivce;
    }

}
