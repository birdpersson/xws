package xws.post.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xws.post.domain.UserCollection;
import xws.post.repository.UserCollectionRepository;

import java.util.List;

@Service
public class UserCollectionService {

	@Autowired
	private UserCollectionRepository userCollectionRepository;

	public UserCollection findById(Long id) {
		return userCollectionRepository.findById(id).orElseGet(null);
	}

	public List<UserCollection> findAll() {
		return userCollectionRepository.findAll();
	}

}
