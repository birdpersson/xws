package xws.post.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xws.post.domain.Post;
import xws.post.repository.PostRepository;

import java.util.List;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;

	public Post findById(Long id) {
		return postRepository.findById(id).orElseGet(null);
	}

	public List<Post> findByUsername(String username) {
		return postRepository.findByUsername(username);
	}

	public List<Post> findAll() {
		return postRepository.findAll();
	}

}
