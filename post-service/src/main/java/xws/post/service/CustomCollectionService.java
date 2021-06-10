package xws.post.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xws.post.domain.CustomCollection;
import xws.post.domain.Post;
import xws.post.dto.CollectionDTO;
import xws.post.repository.CustomCollectionRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CustomCollectionService {

	@Autowired
	private CustomCollectionRepository customCollectionRepository;

	@Autowired
	private PostService postService;

	public CustomCollection findById(Long id) {
		return customCollectionRepository.findById(id).orElseGet(null);
	}

	public List<CustomCollection> findAll() {
		return customCollectionRepository.findAll();
	}

	public CustomCollection create(CollectionDTO collectionDTO) {
		Set<Post> posts = new HashSet<>();
		for (String id : collectionDTO.getPosts()) {
			Post post = postService.findById(Long.parseLong(id));
			posts.add(post);
		}
		CustomCollection c = new CustomCollection();
		c.setCollectionName(collectionDTO.getName());
		c.setPosts(posts);
		return customCollectionRepository.save(c);
	}

}
