package xws.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xws.auth.domain.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);

	@Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(concat('%', ?1,'%')) ")
	List<User> search(String query);
}
