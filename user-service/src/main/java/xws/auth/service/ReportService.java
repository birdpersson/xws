package xws.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xws.auth.domain.Report;
import xws.auth.repository.ReportRepository;

import java.util.List;

@Service
public class ReportService {

	@Autowired
	private ReportRepository reportRepository;

	public List<Report> findAll() {
		return reportRepository.findAll();
	}

	public Report findById(Long id) {
		return reportRepository.findById(id).orElseGet(null);
	}

	public Report create(String issuerId, String subjectId, String reason) {
		Report r = new Report();
		r.setIssuerUsername(issuerId);
		r.setSubjectUsername(subjectId);
		r.setReportReason(reason);
		return reportRepository.save(r);
	}

}
