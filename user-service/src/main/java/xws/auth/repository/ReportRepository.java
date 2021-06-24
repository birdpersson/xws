package xws.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xws.auth.domain.Report;
import xws.auth.domain.User;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
