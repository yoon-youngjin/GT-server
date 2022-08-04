package dev.yoon.gridgetest.domain.admin.api.log;

import dev.yoon.gridgetest.domain.admin.application.log.AdminLogService;
import dev.yoon.gridgetest.domain.admin.dto.board.GetBoardInfoDto;
import dev.yoon.gridgetest.domain.admin.dto.log.GetUserLogRes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static dev.yoon.gridgetest.global.util.Constants.SET_PAGE_ITEM_MAX_COUNT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/log")
public class AdminLogApi {

    private final AdminLogService adminLogService;

    @GetMapping("/users")
    public ResponseEntity<Page<GetUserLogRes>> getUserLog(
            Optional<Integer> page

    ) {
        Pageable pageable = PageRequest.of(page.orElse(0), SET_PAGE_ITEM_MAX_COUNT);
        Page<GetUserLogRes> response = adminLogService.getUserLog(pageable);
        return ResponseEntity.ok(response);

    }
}
