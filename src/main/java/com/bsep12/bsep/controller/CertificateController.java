package com.bsep12.bsep.controller;

import com.bsep12.bsep.dto.CertificateDTO;
import com.bsep12.bsep.model.User;
import com.bsep12.bsep.security.TokenUtils;
import com.bsep12.bsep.service.CertificateService;
import com.bsep12.bsep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/cert")
public class CertificateController {

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private UserService userService;

	@Autowired
	private CertificateService certificateService;

	@PostMapping("/create")
	public ResponseEntity createCertificate(HttpServletRequest request, @RequestBody CertificateDTO certificateDTO) {
//		User u = (User) userService.loadUserByUsername(tokenUtils.getUsernameFromToken(tokenUtils.getToken(request)));
		certificateService.createCertificate(certificateDTO);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("/")
	public ResponseEntity getAll() {
		return ResponseEntity.ok(certificateService.getAll());
	}

	@GetMapping("/ca")
	public ResponseEntity getAllCa() {
		return ResponseEntity.ok(certificateService.getAllCa());
	}

	@GetMapping("/{id}")
	public ResponseEntity getById(@PathVariable String id) {
		return ResponseEntity.ok(certificateService.getById(id));
	}

	@GetMapping("/revoke/{id}")
	public ResponseEntity isRevoked(@PathVariable Long id) {
		return ResponseEntity.ok(certificateService.isRevoked(id));
	}

//	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/revoke/{id}")
	public ResponseEntity revokeCertificate(@PathVariable String id) {
		certificateService.revokeCertificate(id);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	//TODO: getAll, getAllCa, getBy{id}, @PreAuthorize("hasRole('ROLE_ADMIN')")revoke{id}, getRevoked{id}, getValid{id}
}
