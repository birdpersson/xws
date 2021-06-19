package xws.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xws.auth.domain.Verification;

public interface VerificationRepository extends JpaRepository<Verification, Long> {
	Verification findByUsername(String username);
}
