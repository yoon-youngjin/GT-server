package dev.yoon.gridgetest.domain.report.entity;

import dev.yoon.gridgetest.domain.base.BaseTimeEntity;
import dev.yoon.gridgetest.domain.report.model.ServiceType;
import dev.yoon.gridgetest.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "report")
@Getter
@NoArgsConstructor
public class Report extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REPORT_ID")
    private Long Id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ServiceType serviceType;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "FROM_USER_ID", nullable = false)
    private User from;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "TO_USER_ID", nullable = false)
    private User to;

    @Column(nullable = false, length = 20)
    private Long serviceId;

    @Column(nullable = false, length = 200)
    private String reason;

    @Builder
    public Report(ServiceType serviceType, User from, User to, Long serviceId, String reason) {
        this.serviceType = serviceType;
        this.from = from;
        this.to = to;
        this.serviceId = serviceId;
        this.reason = reason;
    }

    public static Report createReport(ServiceType serviceType, User from, User to, Long serviceId, String reason) {

        return Report.builder()
                .serviceType(serviceType)
                .from(from)
                .to(to)
                .serviceId(serviceId)
                .reason(reason)
                .build();
    }

}
