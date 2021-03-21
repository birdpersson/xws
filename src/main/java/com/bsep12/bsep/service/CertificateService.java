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
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.*;
import java.security.cert.CertificateException;
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
		List<Certificate> certificates = certificateRepository.findAll();
		for (Certificate c : certificates) {
			KeyStoreReader ksr = new KeyStoreReader();
			X509Certificate cert = (X509Certificate) ksr.readCertificate(
					"keystore.jks", "pass", c.getId().toString());
			dtos.add(cdc.generateDtoFromCert(cert));
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
			//TODO: check if ca && valid
			/*
			isCa = cert.getBasicConstraints() != -1
			if (isCa && isValid)
			 */
			dtos.add(cdc.generateDtoFromCert(cert));
		}
		return dtos;
	}

	//TODO: check dates
	//TODO: revoke
	//TODO: isValid (check dates && revoked)
	//TODO: getById (use CertToDtoConverter)
	//TODO: getKeys?

	public void createCertificate(CertificateDTO certificateDTO, String uid) {
		//TODO: do validation && check dates
		//TODO: set uid to email if not root

		try {
			KeyPair keyPairSubject = generateKeyPair();
			Certificate certificate = new Certificate();

			certificateRepository.save(certificate);
			certificateDTO.setSerialNumber(certificate.getId().toString());

			SubjectData subjectData = generateSubjectData(certificateDTO, keyPairSubject, uid);
			IssuerData issuerData;

			if (certificateDTO.isRoot())
				issuerData = generateIssuerData(certificateDTO, keyPairSubject.getPrivate(), uid);
			else {
				String lastCertId = Long.toString(certificate.getId());

				if(!checkDates("keystore.jks", "pass", lastCertId, certificateDTO.getIssuerSerialNumber()))
					return;

				KeyStoreReader ksr = new KeyStoreReader();
				issuerData = ksr.readIssuerFromStore("keystore.jks",
						certificateDTO.getIssuerSerialNumber(), "pass".toCharArray(), "pass".toCharArray());
			}

			CertificateGenerator cg = new CertificateGenerator();
			X509Certificate cert = cg.generateCertificate(subjectData, issuerData, certificateDTO.isCa());

			cert.verify(keyPairSubject.getPublic());

			KeyStoreWriter ksw = new KeyStoreWriter();
			ksw.loadKeyStore(null, "pass".toCharArray()); //always gen new keystore (for testing only)
//		ksw.loadKeyStore("keystore.jks", "pass".toCharArray());
			ksw.write(cert.getSerialNumber().toString(), keyPairSubject.getPrivate(), "pass".toCharArray(), cert);
			ksw.saveKeyStore("keystore.jks", "pass".toCharArray());

			System.out.println(cert);
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
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

	private boolean checkDates (String file, String pass, String lastId, String issuerId){
		KeyStoreReader ksr = new KeyStoreReader();
		X509Certificate cert = (X509Certificate) ksr.readCertificate(file, pass, lastId);
		X509Certificate cert2 = (X509Certificate) ksr.readCertificate(file, pass, issuerId);

		Date startDate = cert.getNotBefore();
		Date endDate = cert.getNotAfter();

		Date startDate2 = cert2.getNotBefore();
		Date endDate2 = cert2.getNotAfter();

		if(startDate.after(startDate2) && endDate.before(endDate2))
			return true;
		return false;
	}

//	private boolean checkRevoked(){
//		List<Certificate> c = certificateRepository.findAll();
//		for()
//	}

}
