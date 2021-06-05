package xws.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xws.post.domain.UserCollection;

public interface UserCollectionRepository extends JpaRepository<UserCollection, Long> {
}
