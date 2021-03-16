package com.bsep12.bsep.service;

import com.bsep12.bsep.certificate.data.IssuerData;
import com.bsep12.bsep.certificate.data.SubjectData;
import com.bsep12.bsep.certificate.generators.CertificateGenerator;
import com.bsep12.bsep.certificate.generators.KeyGenerator;
import com.bsep12.bsep.certificate.keystores.KeyStoreWriter;
import com.bsep12.bsep.dto.CertificateDTO;
import com.bsep12.bsep.model.Certificate;
import com.bsep12.bsep.repository.CertificateRepository;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CertificateService {

	@Autowired
	private CertificateRepository certificateRepository;

	public void createCertificate(CertificateDTO certificateDTO) {

		KeyGenerator kg = new KeyGenerator();
		KeyPair keyPairSubject = kg.generateKeyPair();

		Certificate certificate = new Certificate();
		certificateRepository.save(certificate);

		String sn = certificate.getId().toString();
		certificateDTO.setSerialNumber(sn);

		SubjectData subjectData = generateSubjectData(certificateDTO, keyPairSubject);
		//TODO: certificate is self signed only
		IssuerData issuerData = generateIssuerData(certificateDTO, keyPairSubject.getPrivate());

		CertificateGenerator cg = new CertificateGenerator();
		X509Certificate cert = cg.generateCertificate(subjectData, issuerData);

		//TODO: write to keystore
	}

	private IssuerData generateIssuerData(CertificateDTO certificate, PrivateKey issuerKey) {
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		builder.addRDN(BCStyle.CN, certificate.getCommonName());
//		builder.addRDN(BCStyle.SURNAME, certificate.getSurName());
//		builder.addRDN(BCStyle.GIVENNAME, certificate.getGivenName());
		builder.addRDN(BCStyle.O, certificate.getOrganizationName());
		builder.addRDN(BCStyle.OU, certificate.getOrganizationalUnitName());
		builder.addRDN(BCStyle.C, certificate.getCountryName());
		builder.addRDN(BCStyle.E, certificate.getEmail());
		//TODO: UID (USER ID) je ID korisnika (from username)
		builder.addRDN(BCStyle.UID, "654321");

		return new IssuerData(issuerKey, builder.build());
	}

	private SubjectData generateSubjectData(CertificateDTO certificate, KeyPair keyPairSubject) {
		try {
			SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = iso8601Formater.parse(certificate.getStartDate());
			Date endDate = iso8601Formater.parse(certificate.getEndDate());

			String sn = certificate.getSerialNumber();
			X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
			builder.addRDN(BCStyle.CN, certificate.getCommonName());
//		    builder.addRDN(BCStyle.SURNAME, certificate.getSurName());
//		    builder.addRDN(BCStyle.GIVENNAME, certificate.getGivenName());
			builder.addRDN(BCStyle.O, certificate.getOrganizationName());
			builder.addRDN(BCStyle.OU, certificate.getOrganizationalUnitName());
			builder.addRDN(BCStyle.C, certificate.getCountryName());
			builder.addRDN(BCStyle.E, certificate.getEmail());
			//TODO: UID (USER ID) je ID korisnika (from username)
			builder.addRDN(BCStyle.UID, "123456");

			return new SubjectData(keyPairSubject.getPublic(), builder.build(), sn, startDate, endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
