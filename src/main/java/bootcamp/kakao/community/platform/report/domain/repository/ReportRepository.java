package bootcamp.kakao.community.platform.report.domain.repository;

import bootcamp.kakao.community.platform.report.domain.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
