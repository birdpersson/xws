package com.bsep12.bsep.certificate.generators;

import com.bsep12.bsep.dto.CertificateDTO;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;

import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;

public class CertToDtoConverter {
	public CertToDtoConverter() {
	}

	public CertificateDTO generateDtoFromCert(X509Certificate cert) {
		try {
			X500Name subjectName = new JcaX509CertificateHolder(cert).getSubject();

			RDN cn = subjectName.getRDNs(BCStyle.CN)[0];
			RDN o = subjectName.getRDNs(BCStyle.O)[0];
			RDN ou = subjectName.getRDNs(BCStyle.OU)[0];
			RDN c = subjectName.getRDNs(BCStyle.C)[0];
			RDN e = subjectName.getRDNs(BCStyle.E)[0];

			String commonName = IETFUtils.valueToString(cn.getFirst().getValue());
			String organizationName = IETFUtils.valueToString(o.getFirst().getValue());
			String organizationalUnitName = IETFUtils.valueToString(ou.getFirst().getValue());
			String countryName = IETFUtils.valueToString(c.getFirst().getValue());
			String email = IETFUtils.valueToString(e.getFirst().getValue());

			String startDate = cert.getNotBefore().toString();
			String endDate = cert.getNotAfter().toString();

			String usage = cert.getExtendedKeyUsage().get(0); // only added 1 use
			boolean ca = cert.getBasicConstraints() != -1;
			String serialNumber = cert.getSerialNumber().toString();

			X500Name issuerName = new JcaX509CertificateHolder(cert).getIssuer();
			boolean root = false;
			if (issuerName.equals(subjectName))
				root = true;

			RDN icn = issuerName.getRDNs(BCStyle.CN)[0];
			String issuerCommonName = IETFUtils.valueToString(icn.getFirst().getValue());

			CertificateDTO dto = new CertificateDTO();

			dto.setStartDate(startDate);
			dto.setEndDate(endDate);

			dto.setCommonName(commonName);
			dto.setOrganizationName(organizationName);
			dto.setOrganizationalUnitName(organizationalUnitName);
			dto.setCountryName(countryName);
			dto.setEmail(email);
			dto.setUsage(usage);
			dto.setCa(ca);
			dto.setRoot(root);
			dto.setIssuerCommonName(issuerCommonName);

			dto.setSerialNumber(serialNumber);

			return dto;
		} catch (CertificateEncodingException | CertificateParsingException e) {
			e.printStackTrace();
			return null;
		}
	}
}
