package xws.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xws.auth.domain.User;
import xws.auth.dto.NotificationDTO;
import xws.auth.dto.UserRegistrationDTO;
import xws.auth.exception.UsernameNotUniqueException;
import xws.auth.security.TokenUtils;
import xws.auth.service.UserService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	UserService userService;

	@CrossOrigin
	@GetMapping(value = "/search/{query}")
	public ResponseEntity search(@PathVariable String query) {
		return ResponseEntity.ok(userService.search(query));
	}

	@GetMapping(value = "/profile-view/{username}")
	public ResponseEntity getProfileInfo(@PathVariable String username) {
		return ResponseEntity.ok(userService.findByUsername(username));
	}

	@GetMapping(value = "/follow-requests")
	public ResponseEntity getFollowRequests(HttpServletRequest request) {
		String subjectUsername = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
		return ResponseEntity.ok(userService.getFollowRequests(subjectUsername));
	}

	@GetMapping(value = "/my-profile/{username}")
	public ResponseEntity getCheckFollow(@PathVariable String username, HttpServletRequest request) {
		String issuerUsername = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
		return ResponseEntity.ok(userService.checkYourPage(issuerUsername, username));
	}

	@CrossOrigin
	@PostMapping("/signup")
	public ResponseEntity<User> addUser(@RequestBody UserRegistrationDTO userDTO) throws UsernameNotUniqueException {
		return new ResponseEntity(userService.register(userDTO), HttpStatus.CREATED);
	}

	@PostMapping("/notifications/{username}")
	public ResponseEntity<User> notifyUser(@PathVariable("username") String subjectUsername,
	                                       @RequestBody NotificationDTO dto, HttpServletRequest request) {
		String issuerUsername = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
		return new ResponseEntity<>(userService.settings(dto, issuerUsername, subjectUsername), HttpStatus.CREATED);
	}

	@PostMapping("/mute/{username}")
	public ResponseEntity<User> muteUser(@PathVariable("username") String subjectUsername, HttpServletRequest request) {
		String issuerUsername = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
		return new ResponseEntity<>(userService.muteUser(issuerUsername, subjectUsername), HttpStatus.CREATED);
	}

	@PostMapping("/block/{username}")
	public ResponseEntity<User> blockUser(@PathVariable("username") String subjectUsername, HttpServletRequest request) {
		String issuerUsername = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
		return new ResponseEntity<>(userService.blockUser(issuerUsername, subjectUsername), HttpStatus.CREATED);
	}

	@PutMapping("verify/{username}")
	public ResponseEntity<User> verifyUser(@PathVariable String username) {
		User user = userService.findByUsername(username);
		user.setVerified(true); //TODO: move to service
		return new ResponseEntity<>(userService.update(user), HttpStatus.CREATED);
	}

}
