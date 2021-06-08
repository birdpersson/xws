package xws.post.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xws.post.domain.Post;
import xws.post.dto.PostDTO;
import xws.post.service.CommentService;
import xws.post.service.PostService;

import java.util.List;

@RestController
@RequestMapping(value = "/post")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private CommentService commentService;

	@CrossOrigin
	@GetMapping("/{username}")
	public ResponseEntity getAll(@PathVariable String username) {
		return ResponseEntity.ok(postService.findAllByUsername(username));
	}
/*
	@GetMapping("/{id}")
	public ResponseEntity getPost(@PathVariable String id) {
		return ResponseEntity.ok(postService.findById(Long.parseLong(id)));
	}*/

	@CrossOrigin
	@PostMapping("/createPost")
	public ResponseEntity<Post> createPost(@RequestBody PostDTO postDTO) {
		return new ResponseEntity(postService.save(postDTO), HttpStatus.CREATED);
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

	@PostMapping("/{id}/like")
	public ResponseEntity likePost(@PathVariable String id) {
		//TODO: get username form token
		//TODO: add to user collection
		//TODO: add username to likes
		return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
	}

	@PostMapping("/{id}/dislike")
	public ResponseEntity dislikePost(@PathVariable String id) {
		//TODO: get username from token
		//TODO: add to user collection
		//TODO: add username to likes
		return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
	}

	@PostMapping("/{id}/favorite")
	public ResponseEntity favorite(@PathVariable String id) {
		//TODO: get username from token
		//TODO: add to user collection
		return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
	}

	@PostMapping("/{id}/comment")
	public ResponseEntity comment(@PathVariable String id, @RequestBody String text) {
		//TODO: get username from token
		String username = "temp";
		return new ResponseEntity(commentService.save(username,
				postService.findById(Long.parseLong(id)), text), HttpStatus.NOT_IMPLEMENTED);
	}

}
