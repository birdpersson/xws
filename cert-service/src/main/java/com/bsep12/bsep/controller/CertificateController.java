package com.bsep12.bsep.controller;

import com.bsep12.bsep.dto.CertificateDTO;
import com.bsep12.bsep.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/cert")
public class CertificateController {

	@Autowired
	private CertificateService certificateService;

	@PostMapping("/create")
	public ResponseEntity createCertificate(HttpServletRequest request, @RequestBody CertificateDTO certificateDTO) {
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

	//	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/all")
	public ResponseEntity<List<CertificateDTO>> getAll(HttpServletRequest request) {
		return new ResponseEntity<List<CertificateDTO>>(certificateService.getAll(), HttpStatus.OK);
	}

}
