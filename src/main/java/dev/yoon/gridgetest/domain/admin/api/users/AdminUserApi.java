package dev.yoon.gridgetest.domain.admin.api.users;

import dev.yoon.gridgetest.domain.admin.application.users.AdminUserService;
import dev.yoon.gridgetest.domain.admin.dto.users.GetUserDetailRes;
import dev.yoon.gridgetest.domain.admin.dto.users.GetUserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static dev.yoon.gridgetest.global.util.Constants.SET_PAGE_ITEM_MAX_COUNT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/users")
public class AdminUserApi {

    private final AdminUserService adminUserService;

    @GetMapping
    public ResponseEntity<Page<GetUserInfoDto.Response>> getAllUserInfo(
            @Valid GetUserInfoDto.Request request,
            Optional<Integer> page
    ) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, SET_PAGE_ITEM_MAX_COUNT);

        Page<GetUserInfoDto.Response> response = adminUserService.getUsersInfo(pageable, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<GetUserDetailRes> getUserDetailInfo(
            @PathVariable("userId") Long userId
    ) {

        GetUserDetailRes response = adminUserService.getUserDetailInfo(userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> pauseUser(
            @PathVariable("userId") Long userId
    ){

        adminUserService.pauseUser(userId);
        return ResponseEntity.ok().build();
    }


}
