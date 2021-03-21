package com.bsep12.bsep.controller;

import com.bsep12.bsep.dto.CertificateDTO;
import com.bsep12.bsep.model.User;
import com.bsep12.bsep.security.TokenUtils;
import com.bsep12.bsep.service.CertificateService;
import com.bsep12.bsep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
		User u = (User) userService.loadUserByUsername(tokenUtils.getUsernameFromToken(tokenUtils.getToken(request)));
		certificateService.createCertificate(certificateDTO, u.getId().toString());

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PutMapping("/revok")
	public ResponseEntity createCertificate(HttpServletRequest request, String id) {
		certificateService.revokeCertificate("2");

		return new ResponseEntity<>(HttpStatus.OK);
	}
	//TODO: getAll, getAllCa, getBy{id}, @PreAuthorize("hasRole('ROLE_ADMIN')")revoke{id}, getRevoked{id}, getValid{id}
}
