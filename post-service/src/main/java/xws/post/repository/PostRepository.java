package xws.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import xws.post.domain.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findAllByUsername(String username);

		@Query(value="SELECT distinct(ph.hashtags) FROM post_hashtags ph" +
			" WHERE LOWER(ph.hashtags) LIKE LOWER(concat('%', ?1,'%')) " , nativeQuery = true)
	List<String> searchHashtag(String query);

	@Query("SELECT distinct(p.location) FROM Post p" +
			" WHERE LOWER(p.location) LIKE LOWER(concat('%', :query,'%')) ")
	List<String> searchLocation(@Param("query") String query);

	List<Post> findAllByLocation(String location);

	@Query(value = "SELECT * FROM post p,post_hashtags ph WHERE p.id=ph.post_id AND LOWER(ph.hashtags) = LOWER(:hashtag)",nativeQuery = true)
	List<Post> findAllByHashtag(@Param("hashtag")String hashtag);


}
