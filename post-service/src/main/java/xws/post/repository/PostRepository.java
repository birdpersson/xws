package xws.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xws.post.domain.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findByUsername(String username);
}
