package com.qnnect.user.repositories;

import com.qnnect.user.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findAllByUserId(@Param(value = "user_Id") UUID user_Id);
    Report findByUserIdAndReportedId(@Param(value = "userId") UUID userId,
                                   @Param(value = "reportedId")long reportedId);
}
