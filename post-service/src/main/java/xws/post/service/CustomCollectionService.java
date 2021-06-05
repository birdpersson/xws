package xws.post.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xws.post.domain.CustomCollection;
import xws.post.repository.CustomCollectionRepository;

import java.util.List;

@Service
public class CustomCollectionService {

	@Autowired
	private CustomCollectionRepository customCollectionRepository;

	public CustomCollection findById(Long id) {
		return customCollectionRepository.findById(id).orElseGet(null);
	}

	public List<CustomCollection> findAll() {
		return customCollectionRepository.findAll();
	}

}
