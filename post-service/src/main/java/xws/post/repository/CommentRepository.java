package xws.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xws.post.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
