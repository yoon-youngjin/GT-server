package dev.yoon.gridgetest.domain.admin.application.report;

import dev.yoon.gridgetest.domain.admin.dto.report.GetReportInfoRes;
import dev.yoon.gridgetest.domain.report.application.ReportService;
import dev.yoon.gridgetest.domain.report.entity.Report;
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
public class AdminReportService {

    private final ReportService reportService;


    public Page<GetReportInfoRes> getAllReports(Pageable pageable) {
        Page<Report> reports =  reportService.getAllReports(pageable);
        List<GetReportInfoRes> collect = reports.stream()
                .map(GetReportInfoRes::from).collect(Collectors.toList());

        return new PageImpl<>(collect, pageable, reports.getTotalElements());

    }

    @Transactional
    public void deleteReport(Long reportId) {
        reportService.deleteReport(reportId);

    }
}
