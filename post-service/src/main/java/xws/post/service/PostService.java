package xws.post.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xws.post.domain.Post;
import xws.post.domain.UserCollection;
import xws.post.dto.CollectionDTO;
import xws.post.dto.CollectionPostDTO;
import xws.post.dto.GetPostDTO;
import xws.post.dto.PostDTO;
import xws.post.repository.PostRepository;

import java.util.ArrayList;
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
		p.setUsername(username);
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

	public List<Integer> like(Post post, String username) {
		Set<String> likes = post.getLikes();
		Set<String> dislikes = post.getDislikes();

		likes.add(username);
		dislikes.remove(username);

		post.setLikes(likes);
		post.setDislikes(dislikes);
		postRepository.save(post);
		List<Integer> count = new ArrayList<Integer>();
		count.add(likes.size());
		count.add(dislikes.size());
		return count;
	}

	public List<Integer> dislike(Post post, String username) {
		Set<String> dislikes = post.getDislikes();
		Set<String> likes = post.getLikes();

		dislikes.add(username);
		likes.remove(username);

		post.setDislikes(dislikes);
		post.setLikes(likes);
		postRepository.save(post);
		List<Integer> count = new ArrayList<Integer>();
		count.add(likes.size());
		count.add(dislikes.size());
		return count;
	}

	public List<Integer> getLikesDislikes(Post post){
		Set<String> dislikes = post.getDislikes();
		Set<String> likes = post.getLikes();

		List<Integer> count = new ArrayList<Integer>();
		count.add(likes.size());
		count.add(dislikes.size());
		return count;
	}

	public List<GetPostDTO> getFollowingPosts(String username){
		List<Post> posts = postRepository.findAll();
		List<Post> sharedPosts = new ArrayList<>();
		for(Post p : posts){
			if(p.getSharedWith().contains(username)){
				sharedPosts.add(p);
			}
		}
		List<GetPostDTO> dto = new ArrayList<>();
		for(Post p : sharedPosts){
			Long id = p.getId();
			String un = p.getUsername();
			String location = p.getLocation();
			String desc = p.getCaption();
			Date date = p.getCreated();
			List<String> hashtags = p.getHashtags();
			List<String> media = p.getMediaUrls();
			dto.add(new GetPostDTO(id, un, location, desc, hashtags, date, media));
		}
		return dto;
	}




}
