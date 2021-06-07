package xws.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xws.auth.domain.User;
import xws.auth.dto.ChangeInfo;
import xws.auth.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private AuthorityService authService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username);
	}

	public User findByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username);
	}

	public List<User> search(String query){
		return userRepository.search(query);
	}

	public User updateInfo(ChangeInfo dto, User user){
		user.setBio(dto.getBio());
		user.setEmail(dto.getEmail());
		user.setGender(dto.getGender());
		user.setUsername(dto.getUsername());
		user.setName(dto.getName());
		user.setBirthday(dto.getDate());
		user.setWebsite(dto.getWebsite());
		user.setPhone(dto.getPhone());

		return userRepository.save(user);
	}
	
	public User followUser(String issuerId, String subjectId) {
		User issuer = userRepository.findByUsername(issuerId);
		List<User> following = issuer.getFollowing();
		following.add(userRepository.findByUsername(subjectId));
		issuer.setFollowing(following);
		return userRepository.save(issuer);
	}

	public List<String> getFriends(String username){
		User user = userRepository.findByUsername(username);
		List<User> following = user.getFollowing();
		List<String> usernames = new ArrayList<String>();

		for(User u : following){
			usernames.add(u.getUsername());
		}

		return usernames;
	}

}
