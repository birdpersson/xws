package xws.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xws.auth.domain.Authority;
import xws.auth.domain.User;
import xws.auth.dto.UserDTO;
import xws.auth.exception.UsernameNotUniqueException;
import xws.auth.repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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

	public User findByEmail(String email) throws UsernameNotFoundException {
		return userRepository.findByEmail(email);
	}

	public User findByToken(String token) throws UsernameNotFoundException {
		return userRepository.findByToken(token);
	}

	public User create(UserDTO userDTO) throws UsernameNotUniqueException {
		if (userRepository.findByUsername(userDTO.getUsername()) != null)
			throw new UsernameNotUniqueException("Username " + userDTO.getUsername() + " is already registered.");
		User u = new User();
		u.setUsername(userDTO.getUsername());
		u.setSalt(UUID.randomUUID().toString());
		u.setPassword(passwordEncoder.encode(userDTO.getPassword() + u.getSalt()));
		u.setName(userDTO.getName());
		u.setBio(userDTO.getBio());
		u.setBirthday(userDTO.getBirthday());
		u.setGender(userDTO.getGender());
		u.setEmail(userDTO.getEmail());
		u.setWebsite(userDTO.getWebsite());
		u.setPhone(userDTO.getPhone());
		u.setPrivacy(true);
		u.setEnabled(true);
		u.setToken(UUID.randomUUID().toString());
		u.setExpiry(new Date((new Date().getTime() + 300000)));
		u.setRole("USER");

		List<Authority> auth = authService.findByName("ROLE_USER");
		u.setAuthorities(auth);

		return userRepository.save(u);
	}

	public User change(User user) {
		userRepository.save(user);
		return user;
	}

	public User changePassword(User user, String newPassword) {

		user.setPassword(passwordEncoder.encode(newPassword + user.getSalt()));
		userRepository.save(user);
		return user;
	}

	public User followUser(String issuerId, String subjectId) {
		User issuer = userRepository.findByUsername(issuerId);
		User subject = userRepository.findByUsername(subjectId);
		if (subject.isPrivate()) {
			List<User> followers = subject.getFollowers();
			followers.add(issuer);
			subject.setFollowers(followers);
			return userRepository.save(subject);
		} else {
			List<User> following = issuer.getFollowing();
			following.add(subject);
			issuer.setFollowing(following);
			return userRepository.save(issuer);
		}
	}

	public User acceptFollower(String issuerId, String subjectId) {
		User subject = userRepository.findByUsername(subjectId);
		User issuer = userRepository.findByUsername(issuerId);

		List<User> followers = subject.getFollowers();
		List<User> following = issuer.getFollowing();

		followers.remove(issuer);
		following.add(subject);

		issuer.setFollowing(following);
		subject.setFollowers(followers);

		userRepository.save(subject);
		return userRepository.save(issuer);
	}

	public List<User> search(String query) {
		return userRepository.search(query);
	}

}
