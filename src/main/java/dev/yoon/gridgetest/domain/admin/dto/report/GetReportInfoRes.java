package dev.yoon.gridgetest.domain.admin.dto.report;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.yoon.gridgetest.domain.report.entity.Report;
import dev.yoon.gridgetest.domain.report.model.ServiceType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GetReportInfoRes {

    private Long reportId;

    private ServiceType serviceType;

    private Long serviceId;

    private String writerNickname;

    private String reason;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime createdTime;

    @Builder
    public GetReportInfoRes(Long reportId, ServiceType serviceType, Long serviceId, String writerNickname, String reason, LocalDateTime createdTime) {
        this.reportId = reportId;
        this.serviceType = serviceType;
        this.serviceId = serviceId;
        this.writerNickname = writerNickname;
        this.reason = reason;
        this.createdTime = createdTime;
    }


    public static GetReportInfoRes from(Report report) {

        return GetReportInfoRes.builder()
                .reportId(report.getId())
                .serviceType(report.getServiceType())
                .serviceId(report.getServiceId())
                .writerNickname(report.getTo().getNickname().getValue())
                .reason(report.getReason())
                .createdTime(report.getCreateTime())
                .build();
    }
}
