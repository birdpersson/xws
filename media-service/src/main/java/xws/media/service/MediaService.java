package xws.media.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MediaService {

	public List<String> upload(MultipartFile[] multipartFiles, String username) {
		List<String> paths = new ArrayList<>();

		for (MultipartFile multipartFile : multipartFiles) {
			String ext = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
			String fileName = UUID.randomUUID() + "." + ext;

			String home = System.getProperty("user.home");
			String path = home + File.separator + "clone" + File.separator + "images";
			try {
				Path uploadPath = Paths.get(path + username);

				if (!Files.exists(uploadPath))
					Files.createDirectories(uploadPath);

				try (InputStream inputStream = multipartFile.getInputStream()) {
					Path filePath = uploadPath.resolve(fileName);
					Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
				}
				//TODO: convert to urls
				paths.add(path + username + fileName);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return paths;
	}

}
