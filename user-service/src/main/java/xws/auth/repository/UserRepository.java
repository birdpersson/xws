package xws.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xws.auth.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
