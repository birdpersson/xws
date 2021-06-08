package xws.post.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xws.post.domain.CustomCollection;
import xws.post.domain.UserCollection;
import xws.post.dto.CollectionDTO;
import xws.post.service.CustomCollectionService;
import xws.post.service.PostService;
import xws.post.service.UserCollectionService;
import xws.post.util.TokenUtils;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/collection")
public class CollectionController {

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private PostService postService;

	@Autowired
	private UserCollectionService userCollectionService;

	@Autowired
	private CustomCollectionService customCollectionService;

	@PostMapping("/custom")
	public ResponseEntity<UserCollection> createCustom(@RequestBody CollectionDTO dto, HttpServletRequest request) {
		String username = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
		CustomCollection collection = customCollectionService.create(dto);
		return new ResponseEntity<>(userCollectionService.addToCollections(collection, username), HttpStatus.CREATED);
	}

}
