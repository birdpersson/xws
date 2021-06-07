package xws.post.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xws.post.dto.PostDTO;
import xws.post.service.CommentService;
import xws.post.service.PostService;
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
		//TODO: add to user collection
		//TODO: add username to likes
		return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
	}

	@PostMapping("/{id}/dislike")
	public ResponseEntity dislikePost(@PathVariable String id, HttpServletRequest request) {
		String username = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
		//TODO: add to user collection
		//TODO: add username to likes
		return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
	}

	@PostMapping("/{id}/favorite")
	public ResponseEntity favorite(@PathVariable String id, HttpServletRequest request) {
		String username = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
		//TODO: add to user collection
		return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
	}

	@PostMapping("/{id}/comment")
	public ResponseEntity comment(@PathVariable String id, @RequestBody String text, HttpServletRequest request) {
		String username = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
		return new ResponseEntity(commentService.save(username,
				postService.findById(Long.parseLong(id)), text), HttpStatus.CREATED);
	}

}
