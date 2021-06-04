package xws.media.service;

import org.springframework.web.multipart.MultipartFile;

public class MediaService {

	public String upload(MultipartFile[] multipartFiles, String username) {

		for (MultipartFile multipartFile : multipartFiles) {
			if (!multipartFile.isEmpty())
				try {
					//TODO
				} catch (Exception e) {
					e.printStackTrace();
				}
		}

		return null;
	}
}
