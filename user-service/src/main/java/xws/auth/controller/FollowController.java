package xws.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import xws.auth.security.TokenUtils;
import xws.auth.service.UserService;

import javax.servlet.http.HttpServletRequest;

@RestController
public class FollowController {

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	UserService userService;

	@PostMapping("/follow/{username}")
	public ResponseEntity followUser(@PathVariable("username") String subjectUsername, HttpServletRequest request) {
		String issuerUsername = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
		return new ResponseEntity(userService.followUser(issuerUsername, subjectUsername), HttpStatus.CREATED);
	}

	@PostMapping("/accept/{username}")
	public ResponseEntity acceptFollowing(@PathVariable("username") String issuerUsername, HttpServletRequest request) {
		String subjectUsername = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
		return new ResponseEntity(userService.acceptFollower(issuerUsername, subjectUsername), HttpStatus.CREATED);
	}

}
