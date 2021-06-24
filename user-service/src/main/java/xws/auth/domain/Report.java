package xws.auth.domain;

import javax.persistence.*;

@Entity
public class Report {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String issuerUsername;

	@Column
	private String subjectUsername;

	@Column
	private String reportReason;

	public Report() {

	}

	public Report(String username, String repUsername, String reason) {
		this.reportReason = reason;
		this.issuerUsername = username;
		this.subjectUsername = repUsername;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIssuerUsername() {
		return issuerUsername;
	}

	public void setIssuerUsername(String issuerUsername) {
		this.issuerUsername = issuerUsername;
	}

	public String getSubjectUsername() {
		return subjectUsername;
	}

	public void setSubjectUsername(String subjectUsername) {
		this.subjectUsername = subjectUsername;
	}

	public String getReportReason() {
		return reportReason;
	}

	public void setReportReason(String reportReason) {
		this.reportReason = reportReason;
	}

}
