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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/cert")
public class CertificateController {

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private UserService userService;

	@Autowired
	private CertificateService certificateService;

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/create")
	public ResponseEntity createCertificate(HttpServletRequest request, @RequestBody CertificateDTO certificateDTO) {
		User u = (User) userService.loadUserByUsername(tokenUtils.getUsernameFromToken(tokenUtils.getToken(request)));
		certificateService.createCertificate(certificateDTO, u.getId().toString());

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PutMapping("/revoke/{id}")
	public ResponseEntity createCertificate(HttpServletRequest request,@PathVariable String id) {
		certificateService.revokeCertificate(id);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/all")
	public  ResponseEntity<List<CertificateDTO>> getAll(HttpServletRequest request){
		return new ResponseEntity<List<CertificateDTO>>(certificateService.getAll(),HttpStatus.OK);
	}
	//TODO: getAll, getAllCa, getBy{id}, @PreAuthorize("hasRole('ROLE_ADMIN')")revoke{id}, getRevoked{id}, getValid{id}
}
