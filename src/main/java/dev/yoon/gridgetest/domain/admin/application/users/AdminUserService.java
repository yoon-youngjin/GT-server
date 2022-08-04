package dev.yoon.gridgetest.domain.admin.application.users;

import dev.yoon.gridgetest.domain.admin.dto.users.GetUserDetailRes;
import dev.yoon.gridgetest.domain.admin.dto.users.GetUserInfoDto;
import dev.yoon.gridgetest.domain.user.application.UserService;
import dev.yoon.gridgetest.domain.user.application.info.UserInfoService;
import dev.yoon.gridgetest.domain.user.domain.User;
import dev.yoon.gridgetest.domain.user.model.UserState;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminUserService {

    private final UserInfoService userInfoService;
    private final UserService userService;

    public Page<GetUserInfoDto.Response> getUsersInfo(Pageable pageable, GetUserInfoDto.Request request) {
        Page<User> users =  userInfoService.getAllUsersByQuery(pageable, request);
        List<GetUserInfoDto.Response> collect = users.stream()
                .map(GetUserInfoDto.Response::from).collect(Collectors.toList());

        return new PageImpl<>(collect, pageable, users.getTotalElements());

    }

    public GetUserDetailRes getUserDetailInfo(Long userId) {
        User user = userService.getUserById(userId);

        return GetUserDetailRes.from(user);

    }

    public void pauseUser(Long userId) {
        User user = userService.getUserById(userId);
        user.updateUserState(UserState.PAUSE);


    }
}
