package dev.yoon.gridgetest.domain.report.api;

import dev.yoon.gridgetest.domain.answer.dto.ReportAnswerReq;
import dev.yoon.gridgetest.domain.board.dto.ReportBoardReq;
import dev.yoon.gridgetest.domain.report.application.ReportService;
import dev.yoon.gridgetest.global.ApiResult;
import dev.yoon.gridgetest.global.resolver.UserPhone;
import dev.yoon.gridgetest.global.util.ApiUtils;
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

    @PostMapping("/board")
    public ResponseEntity<ApiResult<Void>> reportBoard(
            @RequestBody @Valid ReportBoardReq request,
            @UserPhone String phone
    ) {
        reportService.reportBoard(request, phone);
        return ResponseEntity.ok(ApiUtils.success(null, REPORT_BOARD));

    }

    @PostMapping("/answer")
    public ResponseEntity<ApiResult<Void>> reportAnswer(
            @RequestBody @Valid ReportAnswerReq request,
            @UserPhone String phone
    ) {
        reportService.reportAnswer(request, phone);
        return ResponseEntity.ok(ApiUtils.success(null, REPORT_ANSWER));

    }
}
