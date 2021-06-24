package xws.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xws.auth.domain.Verification;
import xws.auth.dto.VerificationDTO;
import xws.auth.repository.VerificationRepository;

import java.util.List;

@Service
public class VerificationService {

	@Autowired
	private VerificationRepository verificationRepository;

	public List<Verification> findAll() {
		return verificationRepository.findAll();
	}

	public Verification findById(Long id) {
		return verificationRepository.findById(id).orElseGet(null);
	}

	public Verification findByUsername(String username) throws UsernameNotFoundException {
		return verificationRepository.findByUsername(username);
	}

	public Verification create(VerificationDTO dto, String username) {
		Verification v = new Verification();
		v.setUsername(username);
		v.setName(dto.getName());
		v.setCategory(dto.getCategory());
		v.setMediaUrl(dto.getMediaUrl());
		return verificationRepository.save(v);
	}

	public void remove(Long id) {
		verificationRepository.deleteById(id);
	}

}
