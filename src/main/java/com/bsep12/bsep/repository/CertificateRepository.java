package com.bsep12.bsep.repository;

import com.bsep12.bsep.model.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
}
