package dev.yoon.gridgetest.domain.user.api;

import dev.yoon.gridgetest.domain.user.application.info.UserInfoService;
import dev.yoon.gridgetest.domain.user.dto.info.GetUserInfoRes;
import dev.yoon.gridgetest.global.resolver.UserPhone;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserInfoApi {

    private final UserInfoService userInfoService;

    @GetMapping
    public GetUserInfoRes getUserInfo(
            @UserPhone String phone
    ){
        GetUserInfoRes response = userInfoService.getUserInfo(phone);
        return response;
    }

}
