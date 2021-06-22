package xws.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xws.auth.domain.Report;
import xws.auth.security.TokenUtils;
import xws.auth.service.ReportService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	ReportService reportService;

	@GetMapping("/")
	public ResponseEntity<List<Report>> getAll() {
		return new ResponseEntity<>(reportService.findAll(), HttpStatus.OK);
	}

	@PostMapping("/{username}")
	public ResponseEntity<Report> reportUser(@PathVariable("username") String subjectUsername, @RequestBody String reason, HttpServletRequest request) {
		String issuerUsername = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
		return new ResponseEntity<>(reportService.create(issuerUsername, subjectUsername, reason), HttpStatus.CREATED);
	}

}
