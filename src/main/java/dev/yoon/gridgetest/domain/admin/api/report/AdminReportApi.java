package dev.yoon.gridgetest.domain.admin.api.report;

import com.sun.jdi.VoidValue;
import dev.yoon.gridgetest.domain.admin.application.board.AdminBoardService;
import dev.yoon.gridgetest.domain.admin.application.report.AdminReportService;
import dev.yoon.gridgetest.domain.admin.dto.board.GetBoardInfoDto;
import dev.yoon.gridgetest.domain.admin.dto.report.GetReportInfoRes;
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
@RequestMapping("/api/admin/report")
public class AdminReportApi {

    private final AdminReportService adminReportService;


    @GetMapping
    public ResponseEntity<Page<GetReportInfoRes>> getAllBoard(
            Optional<Integer> page
    ) {
        Pageable pageable = PageRequest.of(page.orElse(0), SET_PAGE_ITEM_MAX_COUNT);

        Page<GetReportInfoRes> response = adminReportService.getAllReports(pageable);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{reportId}")
    public ResponseEntity<Void> deleteReport(
            @PathVariable("reportId") Long reportId
    ) {
        adminReportService.deleteReport(reportId);
        return ResponseEntity.ok().build();
    }

}
