package dev.yoon.gridgetest.domain.user.api;


import dev.yoon.gridgetest.domain.user.application.activity.UserActivityService;
import dev.yoon.gridgetest.domain.user.dto.activity.GetUserBoardRes;
import dev.yoon.gridgetest.global.resolver.UserPhone;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserActivityApi {

    private final UserActivityService userActivityService;

    @GetMapping("/boards")
    public List<GetUserBoardRes> getUserBoard(
            @UserPhone String phone
    ){
        List<GetUserBoardRes> response = userActivityService.getUserBoard(phone);
        return response;
    }

}
