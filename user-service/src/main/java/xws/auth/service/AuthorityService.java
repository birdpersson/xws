package xws.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xws.auth.domain.Authority;
import xws.auth.repository.AuthorityRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorityService {

	@Autowired
	private AuthorityRepository authorityRepository;

	public List<Authority> findById(Long id) {
		Authority auth = this.authorityRepository.getOne(id);
		List<Authority> auths = new ArrayList<>();
		auths.add(auth);
		return auths;
	}

	public List<Authority> findByName(String name) {
		Authority auth = this.authorityRepository.findByName(name);
		List<Authority> auths = new ArrayList<>();
		auths.add(auth);
		return auths;
	}

}
