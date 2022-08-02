package dev.yoon.gridgetest.domain.report.application;

import dev.yoon.gridgetest.domain.report.dto.ReportServiceReq;
import dev.yoon.gridgetest.domain.report.entity.Report;
import dev.yoon.gridgetest.domain.report.model.ServiceType;
import dev.yoon.gridgetest.domain.report.repository.ReportRepository;
import dev.yoon.gridgetest.domain.user.application.UserService;
import dev.yoon.gridgetest.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {

    private final UserService userService;
    private final ReportRepository reportRepository;

    @Transactional
    public void report(Report report) {
        reportRepository.save(report);
    }

//    public Boolean alreadyReportedUser(Report report) {
//        return reportRepository.existsByFromAndTo(report.getFrom(), report.getTo());
//    }
//
//    public Boolean alreadyReceivedReport(Report report) {
//        return reportRepository.existsByFromAndServiceTypeAndServiceId(report.getFrom(), report.getServiceType(), report.getServiceId());
//    }

//    public List<Long> getReportsByUser(User user, ServiceType serviceType) {
//        return reportRepository.findAllByFromAndServiceType(user, serviceType).stream().map(Report::getServiceId).collect(Collectors.toList());
//    }


}
