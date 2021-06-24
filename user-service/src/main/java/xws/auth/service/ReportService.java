package xws.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xws.auth.domain.Report;
import xws.auth.dto.ReportDTO;
import xws.auth.repository.ReportRepository;
import xws.auth.repository.UserRepository;

import java.util.List;

@Service
public class ReportService {

	@Autowired
	private ReportRepository reportRepository;

	@Autowired
	private UserRepository userRepository;

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

	public Report save(ReportDTO report) {
		String username = report.getUsername();
		String repUsername = report.getRepUsername();
		String reason = report.getReason();

		System.out.println(username);
		System.out.println(repUsername);
		System.out.println(reason);
		return reportRepository.save(new Report(username, repUsername, reason));
	}

}
