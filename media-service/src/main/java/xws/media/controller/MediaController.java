package xws.media.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping(value = "/media", produces = MediaType.APPLICATION_JSON_VALUE)
public class MediaController {

	@PostMapping("/upload")
	public ResponseEntity uploadFiles(@RequestParam("file") MultipartFile[] multipartFiles) {

		return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
	}

}
