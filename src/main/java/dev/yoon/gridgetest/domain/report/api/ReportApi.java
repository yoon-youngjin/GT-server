package dev.yoon.gridgetest.domain.report.api;

import dev.yoon.gridgetest.domain.answer.dto.ReportAnswerReq;
import dev.yoon.gridgetest.domain.board.dto.ReportBoardReq;
import dev.yoon.gridgetest.domain.report.application.ReportService;
import dev.yoon.gridgetest.global.ApiResult;
import dev.yoon.gridgetest.global.resolver.UserPhone;
import dev.yoon.gridgetest.global.util.ApiUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static dev.yoon.gridgetest.global.util.Constants.*;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/report")
public class ReportApi {

    private final ReportService reportService;

    @Operation(summary = "피드 신고")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", defaultValue ="jwt access token", dataType = "string", value = "jwt access token", required = true, paramType = "header")
    })
    @PostMapping("/board")
    public ResponseEntity<ApiResult<Void>> reportBoard(
            @RequestBody @Valid ReportBoardReq request,
            @UserPhone String phone
    ) {
        reportService.reportBoard(request, phone);
        return ResponseEntity.ok(ApiUtils.success(null, REPORT_BOARD));

    }

    @Operation(summary = "댓글 신고")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", defaultValue ="jwt access token", dataType = "string", value = "jwt access token", required = true, paramType = "header")
    })
    @PostMapping("/answer")
    public ResponseEntity<ApiResult<Void>> reportAnswer(
            @RequestBody @Valid ReportAnswerReq request,
            @UserPhone String phone
    ) {
        reportService.reportAnswer(request, phone);
        return ResponseEntity.ok(ApiUtils.success(null, REPORT_ANSWER));

    }
}
