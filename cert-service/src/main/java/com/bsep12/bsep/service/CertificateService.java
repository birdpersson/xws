package com.bsep12.bsep.service;

import com.bsep12.bsep.certificate.data.IssuerData;
import com.bsep12.bsep.certificate.data.SubjectData;
import com.bsep12.bsep.certificate.generators.CertToDtoConverter;
import com.bsep12.bsep.certificate.generators.CertificateGenerator;
import com.bsep12.bsep.certificate.keystores.KeyStoreReader;
import com.bsep12.bsep.certificate.keystores.KeyStoreWriter;
import com.bsep12.bsep.dto.CertificateDTO;
import com.bsep12.bsep.model.Certificate;
import com.bsep12.bsep.repository.CertificateRepository;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.*;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CertificateService {

	@Autowired
	private CertificateRepository certificateRepository;

	public List<CertificateDTO> getAll() {
		CertToDtoConverter cdc = new CertToDtoConverter();
		List<CertificateDTO> dtos = new ArrayList<>();
		CertificateDTO dto = new CertificateDTO();
		List<Certificate> certificates = certificateRepository.findAll();
		for (Certificate c : certificates) {
			KeyStoreReader ksr = new KeyStoreReader();
			X509Certificate cert = (X509Certificate) ksr.readCertificate(
					"keystore.jks", "pass", c.getId().toString());
			dto = cdc.generateDtoFromCert(cert);
			dto.setRevoked(c.isRevoked());
			dtos.add(dto);
		}
		return dtos;
	}

	public List<CertificateDTO> getAllCa() {
		CertToDtoConverter cdc = new CertToDtoConverter();
		List<CertificateDTO> dtos = new ArrayList<>();
		List<Certificate> certificates = certificateRepository.findAll();
		for (Certificate c : certificates) {
			KeyStoreReader ksr = new KeyStoreReader();
			X509Certificate cert = (X509Certificate) ksr.readCertificate(
					"keystore.jks", "pass", c.getId().toString());
			if (!c.isRevoked())
				if (cert.getBasicConstraints() != -1)
					dtos.add(cdc.generateDtoFromCert(cert));
		}
		return dtos;
	}

	public CertificateDTO getById(String id) {
		CertToDtoConverter cdc = new CertToDtoConverter();
		KeyStoreReader ksr = new KeyStoreReader();
		X509Certificate cert = (X509Certificate) ksr.readCertificate(
				"keystore.jks", "pass", id);
		CertificateDTO dto = cdc.generateDtoFromCert(cert);
		return dto;
	}

	public void revokeCertificate(String id) {
		try {
			KeyStoreReader ksr = new KeyStoreReader();
			Certificate certificate = certificateRepository.findById(Long.parseLong(id)).orElse(null);
			certificate.setRevoked(true);
			certificateRepository.save(certificate);
			X509Certificate cert = (X509Certificate) ksr.readCertificate(
					"keystore.jks", "pass", id);
			X500Name subjectName = new JcaX509CertificateHolder(cert).getSubject();
			List<CertificateDTO> dtos = getAll();
			for (CertificateDTO dto : dtos) {
				X509Certificate certCheck = (X509Certificate) ksr.readCertificate(
						"keystore.jks", "pass", dto.getSerialNumber());
				X500Name issuerName = new JcaX509CertificateHolder(certCheck).getIssuer();
				if (issuerName.equals(subjectName)) {
					if (dto.isRoot())
						continue;
					System.out.println(dto.getSerialNumber() + "Number");

					revokeCertificate(dto.getSerialNumber());
				}
			}

		} catch (CertificateEncodingException e) {
			e.printStackTrace();
		}

	}

	public boolean isRevoked(Long id) {
		Certificate c = certificateRepository.findById(id).orElseGet(null);
		return c.isRevoked();
	}

	public void createCertificate(CertificateDTO dto) {

		KeyPair keyPairSubject = generateKeyPair();
		Certificate certificate = new Certificate();

		certificateRepository.save(certificate);
		dto.setSerialNumber(certificate.getId().toString());

		SubjectData subjectData = generateSubjectData(dto, keyPairSubject);
		IssuerData issuerData;

		if (dto.isRoot())
			issuerData = generateIssuerData(dto, keyPairSubject.getPrivate());
		else {
			//String lastCertId = Long.toString(certificate.getId());

//				if (!checkDates("keystore.jks", "pass", lastCertId, dto.getIssuerSerialNumber()) ||
//						!checkIfIssuerCA("keystore.jks", "pass", dto.getIssuerSerialNumber()) ||
//						!checkRevoked("keystore.jks", "pass", dto.getIssuerSerialNumber()))
//					return;

			KeyStoreReader ksr = new KeyStoreReader();
			issuerData = ksr.readIssuerFromStore("keystore.jks",
					dto.getIssuerSerialNumber(), "pass".toCharArray(), "pass".toCharArray());
		}

		CertificateGenerator cg = new CertificateGenerator();
		X509Certificate cert = cg.generateCertificate(subjectData, issuerData, dto.isCa(), dto.getUsage());

		KeyStoreWriter ksw = new KeyStoreWriter();
//		ksw.loadKeyStore(null, "pass".toCharArray()); //always gen new keystore (for testing only)
		ksw.loadKeyStore("keystore.jks", "pass".toCharArray());
		ksw.write(cert.getSerialNumber().toString(), keyPairSubject.getPrivate(), "pass".toCharArray(), cert);
		ksw.saveKeyStore("keystore.jks", "pass".toCharArray());

		System.out.println(cert);
	}

	private IssuerData generateIssuerData(CertificateDTO certificate, PrivateKey issuerKey) {
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		builder.addRDN(BCStyle.CN, certificate.getCommonName());
		builder.addRDN(BCStyle.O, certificate.getOrganizationName());
		builder.addRDN(BCStyle.OU, certificate.getOrganizationalUnitName());
		builder.addRDN(BCStyle.C, certificate.getCountryName());
		builder.addRDN(BCStyle.E, certificate.getEmail());
		builder.addRDN(BCStyle.UID, certificate.getEmail());

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
			builder.addRDN(BCStyle.O, certificate.getOrganizationName());
			builder.addRDN(BCStyle.OU, certificate.getOrganizationalUnitName());
			builder.addRDN(BCStyle.C, certificate.getCountryName());
			builder.addRDN(BCStyle.E, certificate.getEmail());
			builder.addRDN(BCStyle.UID, certificate.getEmail());

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

	private boolean checkDates(String file, String pass, String lastId, String issuerId) {
		KeyStoreReader ksr = new KeyStoreReader();
		X509Certificate cert = (X509Certificate) ksr.readCertificate(file, pass, lastId);
		X509Certificate cert2 = (X509Certificate) ksr.readCertificate(file, pass, issuerId);

		Date startDate = cert.getNotBefore();
		Date endDate = cert.getNotAfter();

		Date startDate2 = cert2.getNotBefore();
		Date endDate2 = cert2.getNotAfter();

		if (startDate.after(startDate2) && endDate.before(endDate2))
			return true;
		return false;
	}

}
