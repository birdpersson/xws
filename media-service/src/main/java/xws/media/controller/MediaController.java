package xws.media.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import xws.media.service.MediaService;
import xws.media.util.TokenUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/media")
public class MediaController {

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private MediaService mediaService;

	@CrossOrigin
	@PostMapping("/upload")
	public ResponseEntity uploadFiles(@RequestParam("media") MultipartFile[] multipartFiles, HttpServletRequest request) {
		String username = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
		return new ResponseEntity(mediaService.upload(multipartFiles, username), HttpStatus.CREATED);
	}

	@CrossOrigin
	@PostMapping("/upload-files")
	public ResponseEntity upload(@RequestParam("files") MultipartFile[] files, HttpServletRequest request) {
		String message = "";
		String username = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
		try {
			List<String> fileNames = new ArrayList<>();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");
			LocalDateTime now = LocalDateTime.now();
			Arrays.asList(files).stream().forEach(file -> {
				mediaService.save(file,username, dtf.format(now));
				fileNames.add(file.getOriginalFilename());
			});

			message = "Uploaded the files successfully: " + fileNames;
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} catch (Exception e) {
			message = "Fail to upload files!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
	}

	@CrossOrigin
	@GetMapping("/files")
	public ResponseEntity<List<String>> getListFiles(HttpServletRequest request) {
		String username = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");
		LocalDateTime now = LocalDateTime.now();
		List<String> fileInfos = mediaService.loadAll(username, dtf.format(now)).map(path -> {

			String url = MvcUriComponentsBuilder
					.fromMethodName(MediaController.class, "getFile", path.getFileName().toString(),request).build().toString();

			return url;
		}).collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
	}

	@CrossOrigin
	@GetMapping("/files/{filename:.+}")
	public ResponseEntity<Resource> getFile(@PathVariable String filename, HttpServletRequest request) {
		String username = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");
		LocalDateTime now = LocalDateTime.now();
		Resource file = mediaService.load(filename, username, dtf.format(now));

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

}
