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

import javax.servlet.http.HttpServletResponse;

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
	public ResponseEntity<ChangeInfo> getUserInfo(){
		User user = userService.findByUsername("user@gmail.com");
		ChangeInfo info = ChangeInfoMapper.userToChangeInfo(user);
		return ResponseEntity.ok(info);
	}

	@PostMapping("/changeInfo")
	public ResponseEntity changeInfo(@RequestBody ChangeInfo dto){
		User user = userService.findByUsername("user@gmail.com");

		return new ResponseEntity(userService.updateInfo(dto,user), HttpStatus.CREATED);
	}
}
