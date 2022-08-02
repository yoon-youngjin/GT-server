package dev.yoon.gridgetest.domain.report.repository;

import dev.yoon.gridgetest.domain.report.entity.Report;
import dev.yoon.gridgetest.domain.report.model.ServiceType;
import dev.yoon.gridgetest.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

//    Boolean existsByFromAndServiceTypeAndServiceId(User from, ServiceType serviceType, Long serviceId);
//
//    Boolean existsByFromAndTo(User from, User to);
//
//    List<Report> findAllByFromAndServiceType(User from, ServiceType serviceType);

}
