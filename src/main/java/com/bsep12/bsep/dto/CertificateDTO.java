package com.bsep12.bsep.dto;

public class CertificateDTO {

	private String startDate;
	private String endDate;

	private String commonName;
	private String organizationName;
	private String organizationalUnitName;
	private String countryName;
	private String email;
	private boolean isCa;
	private boolean isRoot;
	private boolean isRevoked;

	private String serialNumber;
	private String issuerSerialNumber;

	public CertificateDTO() {
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getOrganizationalUnitName() {
		return organizationalUnitName;
	}

	public void setOrganizationalUnitName(String organizationalUnitName) {
		this.organizationalUnitName = organizationalUnitName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isCa() {
		return isCa;
	}

	public void setCa(boolean ca) {
		isCa = ca;
	}

	public boolean isRoot() {
		return isRoot;
	}

	public void setRoot(boolean root) {
		isRoot = root;
	}

	public boolean isRevoked() {
		return isRevoked;
	}

	public void setRevoked(boolean revoked) {
		isRevoked = revoked;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getIssuerSerialNumber() {
		return issuerSerialNumber;
	}

	public void setIssuerSerialNumber(String issuerSerialNumber) {
		this.issuerSerialNumber = issuerSerialNumber;
	}

}
