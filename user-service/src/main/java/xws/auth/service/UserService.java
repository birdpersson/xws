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
import xws.auth.dto.ChangeInfo;
import xws.auth.dto.UserRegistrationDTO;
import xws.auth.exception.UsernameNotUniqueException;
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
	public User register(UserRegistrationDTO userDTO) throws UsernameNotUniqueException {
		if(userRepository.findByUsername(userDTO.getUsername())!=null)
			throw new UsernameNotUniqueException("Username " + userDTO.getUsername() + " is already registered.");
		User u = new User();
		u.setUsername(userDTO.getUsername());
		u.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		u.setEnabled(true);
		u.setPrivacy(true);
		u.setName(userDTO.getName());
		u.setBio(userDTO.getBio());
		u.setBirthday(userDTO.getBirthday());
		u.setGender(userDTO.getGender());
		u.setEmail(userDTO.getEmail());
		u.setWebsite(userDTO.getWebsite());
		u.setPhone(userDTO.getPhone());

		/*u.setToken(UUID.randomUUID().toString());
		u.setExpiry(new Date((new Date().getTime() + 300000)));*/
		u.setRole("USER");

		List<Authority> auth = authService.findByName("ROLE_USER");
		u.setAuthorities(auth);

		u = userRepository.save(u);
		return u;
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

	public List<String> getFriends(String username){
		User user = userRepository.findByUsername(username);
		List<User> following = user.getFollowing();
		List<String> usernames = new ArrayList<String>();

		for(User u : following){
			usernames.add(u.getUsername());
		}

		return usernames;
	}

  public List<User> search(String query){
		return userRepository.search(query);
	}

}
