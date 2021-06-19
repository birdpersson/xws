package xws.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xws.auth.domain.User;
import xws.auth.dto.UserRegistrationDTO;
import xws.auth.exception.UsernameNotUniqueException;
import xws.auth.service.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping(value = "/search/{query}")
	public ResponseEntity search(@PathVariable String query) {
		return ResponseEntity.ok(userService.search(query));
	}

	@PostMapping("/signup")
	public ResponseEntity<User> addUser(@RequestBody UserRegistrationDTO userDTO) throws UsernameNotUniqueException {
		return new ResponseEntity(userService.register(userDTO), HttpStatus.CREATED);
	}

	@PutMapping("verify/{username}")
	public ResponseEntity<User> verifyUser(@PathVariable String username) {
		User user = userService.findByUsername(username);
		user.setVerified(true); //TODO: move to service
		return new ResponseEntity<>(userService.update(user), HttpStatus.CREATED);
	}

}
