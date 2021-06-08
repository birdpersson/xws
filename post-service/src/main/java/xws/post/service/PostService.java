package xws.post.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xws.post.domain.Post;
import xws.post.dto.PostDTO;
import xws.post.repository.PostRepository;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;

	public Post findById(Long id) {
		return postRepository.findById(id).orElseGet(null);
	}

	public List<Post> findAll() {
		return postRepository.findAll();
	}

	public List<Post> findAllByUsername(String username) {
		return postRepository.findAllByUsername(username);
	}

	public List<String> searchHashtags(String query){ return postRepository.searchHashtag(query);}

	public List<String> searchLocation(String query){ return postRepository.searchLocation(query);}

	public List<Post> findAllByLocation(String location) {
		return postRepository.findAllByLocation(location);
	}

	public  List<Post> findAllByHashtag(String hashtag){ return  postRepository.findAllByHashtag(hashtag);}

	public Post save(PostDTO postDTO, String username) {
		Post p = new Post();


		//TODO: get username from token
		//String username = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
		p.setUsername("user@gmail.com");
		p.setLocation(postDTO.getLocation());
		p.setHashtags(postDTO.getHashtags());
		p.setCaption(postDTO.getCaption());
		p.setPostType(postDTO.getPostType());

		p.setMediaUrls(postDTO.getMediaUrls());
		p.setSharedWith(postDTO.getSharedWith());
		p.setHighlighted(postDTO.getHighlighted());
		p.setCreated(new Date());

		return postRepository.save(p);
	}

	public Post like(Post post, String username) {
		Set<String> likes = post.getLikes();
		Set<String> dislikes = post.getDislikes();

		likes.add(username);
		dislikes.remove(username);

		post.setLikes(likes);
		post.setDislikes(dislikes);

		return postRepository.save(post);
	}

	public Post dislike(Post post, String username) {
		Set<String> dislikes = post.getDislikes();
		Set<String> likes = post.getLikes();

		dislikes.add(username);
		likes.remove(username);

		post.setDislikes(dislikes);
		post.setLikes(likes);

		return postRepository.save(post);
	}

}
