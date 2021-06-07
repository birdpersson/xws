package xws.post.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xws.post.domain.Post;
import xws.post.dto.PostDTO;
import xws.post.service.CommentService;
import xws.post.service.PostService;
import xws.post.service.UserCollectionService;
import xws.post.util.TokenUtils;

import javax.servlet.http.HttpServletRequest;

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


	@GetMapping("/{username}")
	public ResponseEntity getAll(@PathVariable String username) {
		return ResponseEntity.ok(postService.findAllByUsername(username));
	}

	@GetMapping("/{id}")
	public ResponseEntity getPost(@PathVariable String id) {
		return ResponseEntity.ok(postService.findById(Long.parseLong(id)));
	}

	@PostMapping("/")
	public ResponseEntity createPost(@RequestBody PostDTO postDTO, HttpServletRequest request) {
		String username = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
		return new ResponseEntity(postService.save(postDTO, username), HttpStatus.CREATED);
	}

	@PostMapping("/{id}/like")
	public ResponseEntity likePost(@PathVariable String id, HttpServletRequest request) {
		String username = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
		Post post = postService.findById(Long.parseLong(id));
		collectionService.addToLikes(post, username);
		return new ResponseEntity(postService.like(post, username), HttpStatus.CREATED);
	}

	@PostMapping("/{id}/dislike")
	public ResponseEntity dislikePost(@PathVariable String id, HttpServletRequest request) {
		String username = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
		Post post = postService.findById(Long.parseLong(id));
		collectionService.addToDislikes(post, username);
		return new ResponseEntity(postService.dislike(post, username), HttpStatus.CREATED);
	}

	@PostMapping("/{id}/favorite")
	public ResponseEntity favorite(@PathVariable String id, HttpServletRequest request) {
		String username = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
		Post post = postService.findById(Long.parseLong(id));
		return new ResponseEntity(collectionService.addToFavorites(post, username), HttpStatus.CREATED);
	}

	@PostMapping("/{id}/comment")
	public ResponseEntity comment(@PathVariable String id, @RequestBody String text, HttpServletRequest request) {
		String username = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
		return new ResponseEntity(commentService.save(username,
				postService.findById(Long.parseLong(id)), text), HttpStatus.CREATED);
	}

}
