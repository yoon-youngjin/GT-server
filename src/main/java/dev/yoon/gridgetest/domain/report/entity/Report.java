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
    private Long reportId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ServiceType serviceType;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "from_user_id", nullable = false)
    private User from;

    @Column(nullable = false, length = 20)
    private Long serviceId;

    @Column(nullable = false, length = 20)
    private String reason;

    @Builder
    public Report(ServiceType serviceType, User from, Long serviceId, String reason) {
        this.serviceType = serviceType;
        this.from = from;
        this.serviceId = serviceId;
        this.reason = reason;
    }

    public static Report createReport(ServiceType serviceType, User from, Long serviceId, String reason) {

        return Report.builder()
                .serviceType(serviceType)
                .from(from)
                .serviceId(serviceId)
                .reason(reason)
                .build();
    }

}
