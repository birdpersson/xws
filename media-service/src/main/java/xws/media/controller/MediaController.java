package xws.media.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xws.media.service.MediaService;

@RestController
@RequestMapping(value = "/media", produces = MediaType.APPLICATION_JSON_VALUE)
public class MediaController {

	@Autowired
	private MediaService mediaService;

	@PostMapping("/")
	public ResponseEntity uploadFiles(@RequestParam("media") MultipartFile[] multipartFiles) {
		//TODO: get username from token
		String username = "temp";
		return new ResponseEntity(mediaService.upload(multipartFiles, username), HttpStatus.NOT_IMPLEMENTED);
	}

}
