package xws.post.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xws.post.dto.PostDTO;
import xws.post.service.PostService;

@RestController
@RequestMapping(value = "/post")
public class PostController {

	@Autowired
	private PostService postService;

	@GetMapping("/{username}")
	public ResponseEntity getAll(@PathVariable String username) {
		return ResponseEntity.ok(postService.findByUsername(username));
	}

	@PostMapping("/")
	public ResponseEntity createPost(@RequestBody PostDTO postDTO) {
		return new ResponseEntity(postService.save(postDTO), HttpStatus.CREATED);
	}

}
