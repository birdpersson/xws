package com.bsep12.bsep.service;

import com.bsep12.bsep.certificate.data.IssuerData;
import com.bsep12.bsep.certificate.data.SubjectData;
import com.bsep12.bsep.certificate.generators.CertificateGenerator;
import com.bsep12.bsep.certificate.keystores.KeyStoreReader;
import com.bsep12.bsep.certificate.keystores.KeyStoreWriter;
import com.bsep12.bsep.dto.CertificateDTO;
import com.bsep12.bsep.model.Certificate;
import com.bsep12.bsep.repository.CertificateRepository;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.*;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class CertificateService {

	@Autowired
	private CertificateRepository certificateRepository;

	public void createCertificate(CertificateDTO certificateDTO, String uid) {

		KeyPair keyPairSubject = generateKeyPair();
		Certificate certificate = new Certificate();

		certificateRepository.save(certificate);
		certificateDTO.setSerialNumber(certificate.getId().toString());

		SubjectData subjectData = generateSubjectData(certificateDTO, keyPairSubject, uid);
		IssuerData issuerData;

		if (certificateDTO.isRoot())
			issuerData = generateIssuerData(certificateDTO, keyPairSubject.getPrivate(), uid);
		else {
			KeyStoreReader ksr = new KeyStoreReader();
			issuerData = ksr.readIssuerFromStore("keystore.jks",
					certificateDTO.getIssuerSerialNumber(), "pass".toCharArray(), "pass".toCharArray());
		}

		CertificateGenerator cg = new CertificateGenerator();
		X509Certificate cert = cg.generateCertificate(subjectData, issuerData, certificateDTO.isCa());

		KeyStoreWriter ksw = new KeyStoreWriter();
		ksw.loadKeyStore(null, "pass".toCharArray());
//		ksw.loadKeyStore("keystore.jks", "pass".toCharArray());
		ksw.write(cert.getSerialNumber().toString(), keyPairSubject.getPrivate(), "pass".toCharArray(), cert);
		ksw.saveKeyStore("keystore.jks", "pass".toCharArray());

		System.out.println(cert);
	}

	private IssuerData generateIssuerData(CertificateDTO certificate, PrivateKey issuerKey, String uid) {
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		builder.addRDN(BCStyle.CN, certificate.getCommonName());
		builder.addRDN(BCStyle.O, certificate.getOrganizationName());
		builder.addRDN(BCStyle.OU, certificate.getOrganizationalUnitName());
		builder.addRDN(BCStyle.C, certificate.getCountryName());
		builder.addRDN(BCStyle.E, certificate.getEmail());
		builder.addRDN(BCStyle.UID, uid);

		return new IssuerData(issuerKey, builder.build());
	}

	private SubjectData generateSubjectData(CertificateDTO certificate, KeyPair keyPairSubject, String uid) {
		try {
			SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = iso8601Formater.parse(certificate.getStartDate());
			Date endDate = iso8601Formater.parse(certificate.getEndDate());

			String sn = certificate.getSerialNumber();
			X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
			builder.addRDN(BCStyle.CN, certificate.getCommonName());
			builder.addRDN(BCStyle.O, certificate.getOrganizationName());
			builder.addRDN(BCStyle.OU, certificate.getOrganizationalUnitName());
			builder.addRDN(BCStyle.C, certificate.getCountryName());
			builder.addRDN(BCStyle.E, certificate.getEmail());
			builder.addRDN(BCStyle.UID, uid);

			return new SubjectData(keyPairSubject.getPublic(), builder.build(), sn, startDate, endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	private KeyPair generateKeyPair() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			keyGen.initialize(2048, random);
			return keyGen.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
		return null;
	}

}
