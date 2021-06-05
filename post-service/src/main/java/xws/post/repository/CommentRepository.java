package xws.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xws.post.domain.Comment;
import xws.post.domain.Post;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findAllByPost(Post post);
}
