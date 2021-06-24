package xws.post.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xws.post.domain.CustomCollection;
import xws.post.domain.Post;
import xws.post.domain.UserCollection;
import xws.post.dto.CollectionPostDTO;
import xws.post.repository.UserCollectionRepository;

import java.util.ArrayList;
import java.util.Date;
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
		List<Post> favorites = c.getFavorites();
		if(!favorites.contains(post))
			favorites.add(post);
		c.setFavorites(favorites);
		return userCollectionRepository.save(c);
	}

	public UserCollection removeFromFavorites(Post post, String username) {
		UserCollection c = findByUsername(username);
		List<Post> favorites = c.getFavorites();
		favorites.remove(post);
		c.setFavorites(favorites);
		return userCollectionRepository.save(c);
	}

	public UserCollection addToLikes(Post post, String username) {
		UserCollection c = findByUsername(username);
		List<Post> likes = c.getLikes();
		List<Post> dislikes = c.getDislikes();
		likes.add(post);
		dislikes.remove(post);
		c.setLikes(likes);
		c.setDislikes(dislikes);
		return userCollectionRepository.save(c);
	}

	public UserCollection addToDislikes(Post post, String username) {
		UserCollection c = findByUsername(username);
		List<Post> dislikes = c.getDislikes();
		List<Post> likes = c.getLikes();
		dislikes.add(post);
		likes.remove(post);
		c.setLikes(likes);
		c.setDislikes(dislikes);
		return userCollectionRepository.save(c);

	}

	public UserCollection addToCollections(CustomCollection collection, String username) {
		UserCollection c = findByUsername(username);
		List<CustomCollection> collections = c.getCollections();
		collections.add(collection);
		c.setCollections(collections);
		return userCollectionRepository.save(c);
	}

	public List<CollectionPostDTO> getFavoritesForUser(String username){
		UserCollection uc = findByUsername(username);
		List<Post> favorites =  uc.getFavorites();
		List<CollectionPostDTO> dtos = new ArrayList<>();

		for(Post p : favorites){
			Long id = p.getId();
			Date d = p.getCreated();
			List<String> media = p.getMediaUrls();

			dtos.add(new CollectionPostDTO(id, d, media));
		}
		return dtos;
	}

}
