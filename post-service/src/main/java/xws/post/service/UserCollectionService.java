package xws.post.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xws.post.domain.CustomCollection;
import xws.post.domain.Post;
import xws.post.domain.UserCollection;
import xws.post.repository.UserCollectionRepository;

import java.util.List;
import java.util.Set;

@Service
public class UserCollectionService {

	@Autowired
	private UserCollectionRepository userCollectionRepository;

	public UserCollection findById(Long id) {
		return userCollectionRepository.findById(id).orElseGet(null);
	}

	public UserCollection findByUsername(String username) {
		return userCollectionRepository.findByUsername(username);
	}

	public List<UserCollection> findAll() {
		return userCollectionRepository.findAll();
	}

	public UserCollection addToFavorites(Post post, String username) {
		UserCollection c = findByUsername(username);
		Set<Post> favorites = c.getFavorites();
		favorites.add(post);
		c.setFavorites(favorites);
		return userCollectionRepository.save(c);
	}

	public UserCollection addToLikes(Post post, String username) {
		UserCollection c = findByUsername(username);
		Set<Post> likes = c.getLikes();
		likes.add(post);
		c.setLikes(likes);
		return userCollectionRepository.save(c);
	}

	public UserCollection addToDislikes(Post post, String username) {
		UserCollection c = findByUsername(username);
		Set<Post> dislikes = c.getDislikes();
		dislikes.add(post);
		c.setDislikes(dislikes);
		return userCollectionRepository.save(c);

	}

	public UserCollection addToCollections(CustomCollection collection, String username) {
		UserCollection c = findByUsername(username);
		Set<CustomCollection> collections = c.getCollections();
		collections.add(collection);
		c.setCollections(collections);
		return userCollectionRepository.save(c);
	}

}
