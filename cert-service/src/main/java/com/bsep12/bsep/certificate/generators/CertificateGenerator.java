package com.bsep12.bsep.certificate.generators;

import com.bsep12.bsep.certificate.data.IssuerData;
import com.bsep12.bsep.certificate.data.SubjectData;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import java.math.BigInteger;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class CertificateGenerator {
	public CertificateGenerator() {
	}

	public X509Certificate generateCertificate(SubjectData subjectData, IssuerData issuerData, boolean isCa, String usage) {
		try {
			JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
			builder = builder.setProvider("BC");

			ContentSigner contentSigner = builder.build(issuerData.getPrivateKey());

			X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(issuerData.getX500name(),
					new BigInteger(subjectData.getSerialNumber()),
					subjectData.getStartDate(),
					subjectData.getEndDate(),
					subjectData.getX500name(),
					subjectData.getPublicKey());

			BasicConstraints basicConstraints = new BasicConstraints(isCa); // <-- true for CA, false for EndEntity
			certGen.addExtension(new ASN1ObjectIdentifier("2.5.29.19"), true, basicConstraints);

			if (!isCa && usage.equals("ANY")) {
				ExtendedKeyUsage anyUsage = new ExtendedKeyUsage(KeyPurposeId.anyExtendedKeyUsage);
				certGen.addExtension(Extension.extendedKeyUsage, true, anyUsage);
			} else if (!isCa && usage.equals("CLIENT")) {
				ExtendedKeyUsage clientAuth = new ExtendedKeyUsage(KeyPurposeId.id_kp_serverAuth);
				certGen.addExtension(Extension.extendedKeyUsage, true, clientAuth);
			} else if (!isCa && usage.equals("SERVER")) {
				ExtendedKeyUsage serverAuth = new ExtendedKeyUsage(KeyPurposeId.id_kp_serverAuth);
				certGen.addExtension(Extension.extendedKeyUsage, true, serverAuth);
			}

			X509CertificateHolder certHolder = certGen.build(contentSigner);

			JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
			certConverter = certConverter.setProvider("BC");

			return certConverter.getCertificate(certHolder);
		} catch (CertificateEncodingException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (OperatorCreationException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (CertIOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
