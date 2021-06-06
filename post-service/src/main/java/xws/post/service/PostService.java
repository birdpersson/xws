package xws.post.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xws.post.domain.Post;
import xws.post.dto.PostDTO;
import xws.post.repository.PostRepository;

import java.util.Date;
import java.util.List;

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

	public Post save(PostDTO postDTO) {
		Post p = new Post();
		//TODO: get username from token
		p.setUsername(postDTO.getUsername());
		p.setLocation(postDTO.getLocation());
		p.setHashtags(postDTO.getHashtags());
		p.setCaption(postDTO.getCaption());

		p.setMediaUrls(postDTO.getMediaUrls());
		p.setSharedWith(postDTO.getSharedWith());
		p.setHighlighted(postDTO.isHighlighted());
		p.setCreated(new Date());

		return postRepository.save(p);
	}



}
