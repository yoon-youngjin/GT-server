package dev.yoon.gridgetest.domain.admin.api;

import dev.yoon.gridgetest.domain.admin.application.AdminService;
import dev.yoon.gridgetest.domain.admin.dto.GetUserInfoDto;
import dev.yoon.gridgetest.global.resolver.UserPhone;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static dev.yoon.gridgetest.global.util.Constants.SET_PAGE_ITEM_MAX_COUNT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminApi {

    private final AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<Page<GetUserInfoDto.Response>> getUserInfo(
            @Valid GetUserInfoDto.Request request,
            Optional<Integer> page
    ){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, SET_PAGE_ITEM_MAX_COUNT);

        Page<GetUserInfoDto.Response> response = adminService.getUsersInfo(pageable, request);
        return ResponseEntity.ok(response);
    }


}
