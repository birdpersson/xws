package xws.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xws.auth.security.TokenUtils;
import xws.auth.service.UserService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/follow")
public class FollowController {

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	UserService userService;

	@GetMapping("/follow/{username}")
	public ResponseEntity followUser(@PathVariable("username") String subjectUsername, HttpServletRequest request) {
		String issuerUsername = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
		return new ResponseEntity(userService.followUser(issuerUsername, subjectUsername), HttpStatus.CREATED);
	}

	@PostMapping("/accept/{username}")
	public ResponseEntity acceptFollowing(@PathVariable("username") String issuerUsername, HttpServletRequest request) {
		String subjectUsername = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
		return new ResponseEntity(userService.acceptFollower(issuerUsername, subjectUsername), HttpStatus.CREATED);
	}

	@PostMapping("/deny/{username}")
	public ResponseEntity denyFollowing(@PathVariable("username") String issuerUsername, HttpServletRequest request) {
		String subjectUsername = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
		return new ResponseEntity(userService.denyFollower(issuerUsername, subjectUsername), HttpStatus.CREATED);
	}

	@GetMapping(value = "/profile-view/{username}")
	public ResponseEntity getCheckFollow(@PathVariable String username, HttpServletRequest request) {
		String issuerUsername = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
		return ResponseEntity.ok(userService.checkFollowing(issuerUsername, username));
	}

	@GetMapping(value = "/follow-request/{username}")
	public ResponseEntity getCheckFollowRequest(@PathVariable String username, HttpServletRequest request) {
		String issuerUsername = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
		return ResponseEntity.ok(userService.checkFollowRequest(issuerUsername, username));
	}

}
