package xws.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xws.auth.domain.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
	Authority findByName(String name);
}
