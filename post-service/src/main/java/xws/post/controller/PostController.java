package xws.post.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xws.post.domain.Comment;
import xws.post.domain.Post;
import xws.post.domain.UserCollection;
import xws.post.dto.CollectionPostDTO;
import xws.post.dto.CommentDTO;
import xws.post.dto.GetPostDTO;
import xws.post.dto.PostDTO;
import xws.post.mapper.PostMapper;
import xws.post.service.CommentService;
import xws.post.service.PostService;
import xws.post.service.UserCollectionService;
import xws.post.util.TokenUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import java.util.List;

@RestController
@RequestMapping(value = "/post")
public class PostController {

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private PostService postService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private UserCollectionService collectionService;

	@CrossOrigin
	@GetMapping("/{username}")
	public ResponseEntity<List<Post>> getAll(@PathVariable String username) {
		return new ResponseEntity<>(postService.findAllByUsername(username), HttpStatus.OK);
	}
/*
	@GetMapping("/{id}")
	public ResponseEntity getPost(@PathVariable String id) {
		return ResponseEntity.ok(postService.findById(Long.parseLong(id)));
	}*/

	@CrossOrigin
	@PostMapping("/createPost")
	public ResponseEntity<Post> createPost(@RequestBody PostDTO postDTO, HttpServletRequest request) {
		String username = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
		return new ResponseEntity<>(postService.save(postDTO, username), HttpStatus.CREATED);
	}

	@GetMapping("/search/hashtags/{query}")
	public ResponseEntity<List<String>> searchHashtags(@PathVariable String query){
		return  ResponseEntity.ok(postService.searchHashtags(query));
	}

	@GetMapping("/search/location/{query}")
	public ResponseEntity<List<String>> searchLocation(@PathVariable String query){
		return  ResponseEntity.ok(postService.searchLocation(query));
	}

	@GetMapping("all/location/{location}")
	public ResponseEntity findAllByLocation(@PathVariable String location){
		return ResponseEntity.ok(postService.findAllByLocation(location));
	}

	@GetMapping("all/hashtags/{hashtag}")
	public ResponseEntity findAllByHashtah(@PathVariable String hashtag){
		return ResponseEntity.ok(postService.findAllByHashtag(hashtag));
	}

	@GetMapping("/{id}/like")
	public ResponseEntity<List<Integer>> likePost(@PathVariable String id, HttpServletRequest request) {
		String username = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
		Post post = postService.findById(Long.parseLong(id));
		collectionService.addToLikes(post, username);
		return new ResponseEntity<List<Integer>>(postService.like(post, username), HttpStatus.CREATED);
	}

	@GetMapping("/collections")
	public ResponseEntity findAllCollections( HttpServletRequest request) {
		String username = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
		return new ResponseEntity(collectionService.findByUsername(username), HttpStatus.OK);
	}

	@GetMapping("/{id}/dislike")
	public ResponseEntity<List<Integer>> dislikePost(@PathVariable String id, HttpServletRequest request) {
		String username = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
		Post post = postService.findById(Long.parseLong(id));
		collectionService.addToDislikes(post, username);
		return new ResponseEntity<List<Integer>>(postService.dislike(post, username), HttpStatus.CREATED);
	}

	@PostMapping("/{id}/favorite")
	public ResponseEntity<UserCollection> favorite(@PathVariable String id, HttpServletRequest request) {
		String username = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
		Post post = postService.findById(Long.parseLong(id));
		return new ResponseEntity<>(collectionService.addToFavorites(post, username), HttpStatus.CREATED);
	}

	@PostMapping("/{id}/unfavorite")
	public ResponseEntity<UserCollection> unfavorite(@PathVariable String id, HttpServletRequest request) {
		String username = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
		Post post = postService.findById(Long.parseLong(id));
		return new ResponseEntity<>(collectionService.removeFromFavorites(post, username), HttpStatus.CREATED);
	}

	@CrossOrigin
	@PostMapping("/{id}/comment")
	public ResponseEntity comment(@PathVariable String id, @RequestBody String text, HttpServletRequest request) {
		String username = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
		Post post = postService.findById(Long.parseLong(id));
		return new ResponseEntity<>(commentService.save(username, post, text), HttpStatus.CREATED);
	}

	@CrossOrigin
	@GetMapping("/getComments/{id}")
	public ResponseEntity<CommentDTO> getComments(@PathVariable String id){

		return new ResponseEntity(commentService.getCommentsForPost(id), HttpStatus.OK);
	}

	@GetMapping("getLikeDislike/{id}")
	public ResponseEntity<List<Integer>> getLikesDislikes(@PathVariable String id){
		Post post = postService.findById(Long.parseLong(id));
		return  new ResponseEntity<List<Integer>>(postService.getLikesDislikes(post), HttpStatus.OK);
	}

	@GetMapping("/getFollowingPosts")
	public ResponseEntity<GetPostDTO> getFollowingPosts(HttpServletRequest request){
		String username = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
		return new ResponseEntity(postService.getFollowingPosts(username),HttpStatus.OK);
	}

	@GetMapping("getPostById/{id}")
	public ResponseEntity<GetPostDTO> getPostById(@PathVariable String id){
		Post p = postService.findById(Long.parseLong(id));
		return new ResponseEntity(PostMapper.postToGetPostDTO(p), HttpStatus.OK);
	}
}
