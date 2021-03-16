package com.bsep12.bsep.model;

import javax.persistence.*;

@Entity
public class Certificate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private boolean isRevoked;

	@Column
	private String revokeReason;

	public Certificate() {
		this.isRevoked = false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isRevoked() {
		return isRevoked;
	}

	public void setRevoked(boolean revoked) {
		isRevoked = revoked;
	}

	public String getRevokeReason() {
		return revokeReason;
	}

	public void setRevokeReason(String revokeReason) {
		this.revokeReason = revokeReason;
	}

}
