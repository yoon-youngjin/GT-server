package dev.yoon.gridgetest.domain.admin.api.report;

import dev.yoon.gridgetest.domain.admin.application.report.AdminReportService;
import dev.yoon.gridgetest.domain.admin.dto.report.GetReportInfoRes;
import dev.yoon.gridgetest.global.ApiResult;
import dev.yoon.gridgetest.global.util.ApiUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static dev.yoon.gridgetest.global.util.Constants.*;
import static dev.yoon.gridgetest.global.util.Constants.SET_PAGE_ITEM_MAX_COUNT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/report")
public class AdminReportApi {

    private final AdminReportService adminReportService;

    @Operation(summary = "관리자 - 신고 전체 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", defaultValue ="jwt access token", dataType = "string", value = "jwt access token", required = true, paramType = "header")
    })
    @GetMapping
    public ResponseEntity<ApiResult<Page<GetReportInfoRes>>> getAllReport(
            Optional<Integer> page
    ) {
        Pageable pageable = PageRequest.of(page.orElse(0), SET_PAGE_ITEM_MAX_COUNT);

        Page<GetReportInfoRes> response = adminReportService.getAllReports(pageable);
        return ResponseEntity.ok(ApiUtils.success(response, GET_ALL_REPORT));
    }

    @Operation(summary = "관리자 - 신고 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", defaultValue ="jwt access token", dataType = "string", value = "jwt access token", required = true, paramType = "header")
    })
    @DeleteMapping("{reportId}")
    public ResponseEntity<ApiResult<Void>> deleteReport(
            @PathVariable("reportId") Long reportId
    ) {
        adminReportService.deleteReport(reportId);
        return ResponseEntity.ok(ApiUtils.success(null, DELETE_REPORT));
    }

}
