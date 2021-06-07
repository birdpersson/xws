package xws.media.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xws.media.service.MediaService;
import xws.media.util.TokenUtils;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/media")
public class MediaController {

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private MediaService mediaService;

	@PostMapping("/upload")
	public ResponseEntity uploadFiles(@RequestParam("media") MultipartFile[] multipartFiles, HttpServletRequest request) {
		String username = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
		System.out.println(username);
		return new ResponseEntity(mediaService.upload(multipartFiles, username), HttpStatus.CREATED);
	}

}
