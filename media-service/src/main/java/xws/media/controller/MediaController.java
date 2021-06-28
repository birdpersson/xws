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
import java.io.File;
import java.io.FilenameFilter;
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
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");
		LocalDateTime now = LocalDateTime.now();

		try {
			List<String> fileNames = new ArrayList<>();

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
	@GetMapping("/postFiles")
	public ResponseEntity<List<String>> getPostFiles(HttpServletRequest request) {
		String username = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
//		String home = System.getProperty("user.home");
//		String p = home + File.separator + "clone" + File.separator + "images" + File.separator + username;
//		File f = new File(p);
//		String[] directories = f.list(new FilenameFilter() {
//			@Override
//			public boolean accept(File current, String name) {
//				return new File(current, name).isDirectory();
//			}
//		});
//
//		int id = 1;
//		if(directories.length > 1) {
//			id = directories.length ;
//		}
//		int finalId = id;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");
		LocalDateTime now = LocalDateTime.now();
		List<String> fileInfos = mediaService.loadAll(username, dtf.format(now)).map(path -> {

			String url = MvcUriComponentsBuilder
					.fromMethodName(MediaController.class, "getFile", path.getFileName().toString(),username, dtf.format(now)).build().toString();

			return url;
		}).collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
	}

//	@CrossOrigin
//	@GetMapping("/listFiles")
//	public ResponseEntity<List<String>> getListFiles(HttpServletRequest request) {
//		String username = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
//		List<String> fileInfos = mediaService.loadAll(username).map(path -> {
//
//			String url = MvcUriComponentsBuilder
//					.fromMethodName(MediaController.class, "getFile", path.getFileName().toString(),username).build().toString();
//
//			return url;
//		}).collect(Collectors.toList());
//
//		return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
//	}

	@CrossOrigin
	@GetMapping("/files/{username}/{date}/{filename:.+}")
	public ResponseEntity<Resource> getFile(@PathVariable String filename,@PathVariable String username,
											@PathVariable String date) {
//		String username = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
		Resource file = mediaService.load(filename, username,date);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

}
