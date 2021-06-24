package xws.media.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class MediaService {

	@Autowired
	private Environment env;
	private final Path root = Paths.get("uploads");


	public List<String> upload(MultipartFile[] multipartFiles, String username) {
		List<String> paths = new ArrayList<>();

		for (MultipartFile multipartFile : multipartFiles) {
			String ext = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
			String fileName = UUID.randomUUID() + "." + ext;

			String home = System.getProperty("user.home");
			String path = home + File.separator + "clone" + File.separator + "images" + File.separator + username;
			try {
				Path uploadPath = Paths.get(path);

				if (!Files.exists(uploadPath))
					Files.createDirectories(uploadPath);

				try (InputStream inputStream = multipartFile.getInputStream()) {
					Path filePath = uploadPath.resolve(fileName);
					Resource resource = new UrlResource(filePath.toUri());
					System.out.println(resource);
					Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
				}
				//TODO: convert to urls
//				String hostName = InetAddress.getLocalHost().getHostName();
//				String port = env.getProperty("local.server.port");
//				String fileUrl = String.format("http://%s:%s%s/%s", hostName, port, path, fileName);
//
//				paths.add(fileUrl);
				paths.add(path + File.separator + fileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return paths;
	}




	public void save(MultipartFile file, String username, String date) {
		String ext = FilenameUtils.getExtension(file.getOriginalFilename());
		String fileName = UUID.randomUUID() + "." + ext;
		System.out.println(date);
		String home = System.getProperty("user.home");
		String path = home + File.separator + "clone" + File.separator + "images" + File.separator + username + File.separator + date;
		try {
			Path uploadPath = Paths.get(path);
			if (!Files.exists(uploadPath))
				Files.createDirectories(uploadPath);

			Files.copy(file.getInputStream(), uploadPath.resolve(fileName));
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
	}


	public Resource load(String filename, String username, String date) {

		String home = System.getProperty("user.home");
		String path = home + File.separator + "clone" + File.separator + "images" + File.separator + username + File.separator + date;
		try {
			Path getPath = Paths.get(path);
			Path file = getPath.resolve(filename);
			System.out.println(file);
			Resource resource = new UrlResource(file.toUri());

			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("Could not read the file!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}


	public Stream<Path> loadAll(String username, String date) {


		String home = System.getProperty("user.home");
		String filePath = home + File.separator + "clone" + File.separator + "images" + File.separator + username + File.separator + date;
		try {
			Path getPath = Paths.get(filePath);
			Stream<Path> stream = Files.walk(getPath, 2).filter(path -> !path.equals(getPath)).map(getPath::relativize);
			return stream;
		} catch (IOException e) {
			throw new RuntimeException("Could not load the files!");
		}
	}

}
