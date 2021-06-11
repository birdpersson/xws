package xws.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import xws.auth.domain.User;
import xws.auth.domain.UserTokenState;
import xws.auth.dto.ChangeInfo;
import xws.auth.mapper.ChangeInfoMapper;
import xws.auth.security.TokenUtils;
import xws.auth.security.auth.JwtAuthenticationRequest;
import xws.auth.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<UserTokenState> createAuthenticationToken(
			@RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) {

		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				authenticationRequest.getUsername(),
				authenticationRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		User user = (User) authentication.getPrincipal();
		String jwt = tokenUtils.generateToken(user.getUsername(), user.getRole());
		int expiresIn = tokenUtils.getExpiredIn();

		return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
	}

	@GetMapping("/userInfo")
	public ResponseEntity<ChangeInfo> getUserInfo(HttpServletRequest request){
		String username = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));

		User user = userService.findByUsername(username);
		ChangeInfo info = ChangeInfoMapper.userToChangeInfo(user);
		return ResponseEntity.ok(info);
	}

	@PostMapping("/changeInfo")
	public ResponseEntity changeInfo(@RequestBody ChangeInfo dto, HttpServletRequest request){
		String username = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
		User user = userService.findByUsername(username);
		List<User> users = userService.findAll();
		List<String> usernames = new ArrayList<>();
		for(User u : users){
			usernames.add(u.getUsername());
		}
		if(usernames.contains(dto.getUsername())){
			return new ResponseEntity("Username already exists",HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(userService.updateInfo(dto,user), HttpStatus.CREATED);
	}

	@GetMapping("/getFriends")
	public ResponseEntity<List<String>> getFriends(HttpServletRequest request){
		//String username = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
		return ResponseEntity.ok(userService.getFriends("user@gmail.com"));

	}
}
