package xws.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xws.auth.domain.Verification;
import xws.auth.dto.VerificationDTO;
import xws.auth.security.TokenUtils;
import xws.auth.service.VerificationService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/verification")
public class VerificationController {

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	VerificationService verificationService;

	@GetMapping("/")
	public ResponseEntity<List<Verification>> getAll() {
		return ResponseEntity.ok(verificationService.findAll());
	}

	@PostMapping("/")
	public ResponseEntity<Verification> sendRequest(VerificationDTO dto, HttpServletRequest request) {
		String username = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
		return new ResponseEntity<>(verificationService.create(dto, username), HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteRequest(@PathVariable String id) {
		Verification verification = verificationService.findById(Long.parseLong(id));
		if (verification == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else {
			verificationService.remove(Long.parseLong(id));
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

}
