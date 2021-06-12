package xws.auth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import xws.auth.domain.User;
import xws.auth.domain.UserTokenState;
import xws.auth.dto.UserDTO;
import xws.auth.exception.UsernameNotUniqueException;
import xws.auth.security.TokenUtils;
import xws.auth.security.auth.JwtAuthenticationRequest;
import xws.auth.service.EmailService;
import xws.auth.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	private static Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;

	@PostMapping("/login")
	public ResponseEntity<UserTokenState> createAuthenticationToken(
			@RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) {

		User u = userService.findByUsername(authenticationRequest.getUsername());

		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				authenticationRequest.getUsername(), authenticationRequest.getPassword() + u.getSalt()));
		logger.warn("Cocoon");
		SecurityContextHolder.getContext().setAuthentication(authentication);

		User user = (User) authentication.getPrincipal();
		String jwt = tokenUtils.generateToken(user.getUsername(), user.getRole());
		int expiresIn = tokenUtils.getExpiredIn();

		logger.info(user.getId() + ": " + "User logged in successfully");
		return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
	}

	@PostMapping("/signup")
	public ResponseEntity<User> addUser(@RequestBody UserDTO userDTO) throws UsernameNotUniqueException {

		User existUser = userService.findByUsername(userDTO.getUsername());
		if (existUser != null)
			//TESTING ONLY - don't return user info
			return new ResponseEntity<>(existUser, HttpStatus.CONFLICT);

		User createdUser = userService.create(userDTO);
		try {
			emailService.sendMail(createdUser);
		} catch (MailException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}

	@GetMapping("/verify")
	public ResponseEntity<User> verifyUser(@RequestParam("token") String token) {
		User existUser = userService.findByToken(token);
		if (existUser == null || existUser.getExpiry().getTime() < new Date().getTime())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		existUser.setEnabled(true);
		existUser.setToken(null);
		existUser.setExpiry(null);
		userService.change(existUser);

		//TESTING ONLY
		return new ResponseEntity<>(existUser, HttpStatus.CREATED);
	}

	@PostMapping("/forgot-password")
	public ResponseEntity forgotPassword(@RequestBody String username) {
		User existUser = userService.findByUsername(username);
		if (existUser == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		existUser.setToken(UUID.randomUUID().toString());
		existUser.setExpiry(new Date((new Date().getTime() + 300000)));
		userService.change(existUser);
		try {
			emailService.sendMail2(existUser);
		} catch (MailException e) {
			e.printStackTrace();
		}

		return new ResponseEntity(HttpStatus.CREATED);
	}

	@PostMapping("/reset-password")
	public ResponseEntity resetPassword(@RequestBody String password, String token) {
		User existUser = userService.findByToken(token);
		if (existUser == null || existUser.getExpiry().getTime() < new Date().getTime())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		existUser.setToken(null);
		existUser.setExpiry(null);
		userService.changePassword(existUser, password);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
