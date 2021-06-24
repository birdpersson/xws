package xws.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xws.auth.domain.Report;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
	List<Report> findByIssuerUsername(String issuerUsername);

	List<Report> findBySubjectUsername(String subjectUsername);
}
