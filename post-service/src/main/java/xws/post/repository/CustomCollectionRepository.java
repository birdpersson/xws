package xws.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xws.post.domain.CustomCollection;

public interface CustomCollectionRepository extends JpaRepository<CustomCollection, Long> {
}
