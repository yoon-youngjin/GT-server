package dev.yoon.gridgetest.domain.admin.api.log;

import dev.yoon.gridgetest.domain.admin.application.log.AdminLogService;
import dev.yoon.gridgetest.domain.admin.dto.log.GetLogRes;
import dev.yoon.gridgetest.domain.report.model.ServiceType;
import dev.yoon.gridgetest.global.ApiResult;
import dev.yoon.gridgetest.global.util.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static dev.yoon.gridgetest.global.util.Constants.*;
import static dev.yoon.gridgetest.global.util.Constants.SET_PAGE_ITEM_MAX_COUNT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/log")
public class AdminLogApi {

    private final AdminLogService adminLogService;

    @GetMapping("/users")
    public ResponseEntity<ApiResult<Page<GetLogRes>>> getUserLog(
            Optional<Integer> page

    ) {
        Pageable pageable = PageRequest.of(page.orElse(0), SET_PAGE_ITEM_MAX_COUNT);
        Page<GetLogRes> response = adminLogService.getLogByServiceType(pageable, ServiceType.USER);
        return ResponseEntity.ok(ApiUtils.success(response, GET_USER_LOG));

    }

    @GetMapping("/board")
    public ResponseEntity<ApiResult<Page<GetLogRes>>> getBoardLog(
            Optional<Integer> page

    ) {
        Pageable pageable = PageRequest.of(page.orElse(0), SET_PAGE_ITEM_MAX_COUNT);
        Page<GetLogRes> response = adminLogService.getLogByServiceType(pageable, ServiceType.BOARD);
        return ResponseEntity.ok(ApiUtils.success(response, GET_BOARD_LOG));

    }

    @GetMapping("/report")
    public ResponseEntity<ApiResult<Page<GetLogRes>>> getReportLog(
            Optional<Integer> page

    ) {
        Pageable pageable = PageRequest.of(page.orElse(0), SET_PAGE_ITEM_MAX_COUNT);
        Page<GetLogRes> response = adminLogService.getLogByServiceType(pageable, ServiceType.REPORT);
        return ResponseEntity.ok(ApiUtils.success(response, GET_REPORT_LOG));


    }

    @GetMapping("/answer")
    public ResponseEntity<ApiResult<Page<GetLogRes>>> getAnswerLog(
            Optional<Integer> page

    ) {
        Pageable pageable = PageRequest.of(page.orElse(0), SET_PAGE_ITEM_MAX_COUNT);
        Page<GetLogRes> response = adminLogService.getLogByServiceType(pageable, ServiceType.ANSWER);
        return ResponseEntity.ok(ApiUtils.success(response, GET_ANSWER_LOG));


    }


}
