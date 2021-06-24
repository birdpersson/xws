package xws.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xws.auth.domain.Notifications;
import xws.auth.domain.User;
import xws.auth.dto.CheckFollowDTO;
import xws.auth.dto.NotificationDTO;
import xws.auth.dto.ProfileDTO;
import xws.auth.dto.UserRegistrationDTO;
import xws.auth.exception.UsernameNotUniqueException;
import xws.auth.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

	public User update(User user) {
		return userRepository.save(user);
	}

	public User updateInfo(ProfileDTO dto, User user) {
		user.setBio(dto.getBio());
		user.setEmail(dto.getEmail());
		user.setUsername(dto.getUsername());
		user.setGender(dto.getGender());
		user.setUsername(dto.getUsername());
		user.setName(dto.getName());
		user.setBirthday(dto.getDate());
		user.setWebsite(dto.getWebsite());
		user.setPhone(dto.getPhone());

		user.setAllowMessages(dto.getAllowMessages());
		user.setAllowTags(dto.getAllowTags());
		user.setPrivacy(dto.isPrivate());

		return userRepository.save(user);
	}

	public User register(UserRegistrationDTO userDTO) throws UsernameNotUniqueException {
		if (userRepository.findByUsername(userDTO.getUsername()) != null)
			throw new UsernameNotUniqueException("Username " + userDTO.getUsername() + " is already registered.");
		User u = new User();
		u.setUsername(userDTO.getUsername());
		u.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		u.setEnabled(true);
		u.setPrivacy(true);
		u.setAllowTags(true);
		u.setAllowMessages(true);
		u.setName(userDTO.getName());
		u.setBio(userDTO.getBio());
		u.setBirthday(userDTO.getBirthday());
		u.setGender(userDTO.getGender());
		u.setEmail(userDTO.getEmail());
		u.setWebsite(userDTO.getWebsite());
		u.setPhone(userDTO.getPhone());

		u.setRole("USER");

//		List<Authority> auth = authService.findByName("ROLE_USER");
//		u.setAuthorities(auth);

		u = userRepository.save(u);
		return u;
	}

	public User settings(NotificationDTO dto, String issuerId, String subjectId) {
		User issuer = userRepository.findByUsername(issuerId);

		Notifications n = new Notifications();
		n.setUsername(subjectId);
		n.setStories(dto.getStories());
		n.setPosts(dto.getPosts());
		n.setComments(dto.getComments());
		n.setMessages(dto.getMessages());

		List<Notifications> settings = issuer.getSettings();
		settings.add(n);

		return userRepository.save(issuer);
	}

	public User muteUser(String issuerId, String subjectId) {
		User issuer = userRepository.findByUsername(issuerId);
		User subject = userRepository.findByUsername(subjectId);

		List<User> muted = issuer.getMuted();
		muted.add(subject);
		return userRepository.save(issuer);
	}

	public User blockUser(String issuerId, String subjectId) {
		User issuer = userRepository.findByUsername(issuerId);
		User subject = userRepository.findByUsername(subjectId);

		List<User> blocked = issuer.getBlocked();
		blocked.add(subject);
		return userRepository.save(issuer);
	}

	public User followUser(String issuerId, String subjectId) {
		User issuer = userRepository.findByUsername(issuerId);
		User subject = userRepository.findByUsername(subjectId);
		if (subject.isPrivate()) {
			Set<String> followers = subject.getFollowers();
			followers.add(issuer.getUsername());
			subject.setFollowers(followers);
			return userRepository.save(subject);
		} else {
			Set<String> following = issuer.getFollowing();
			following.add(subject.getUsername());
			issuer.setFollowing(following);
			return userRepository.save(issuer);
		}
	}

	public User acceptFollower(String issuerId, String subjectId) {
		User subject = userRepository.findByUsername(subjectId);
		User issuer = userRepository.findByUsername(issuerId);

		Set<String> followers = subject.getFollowers();
		Set<String> following = issuer.getFollowing();

		followers.remove(issuer.getUsername());
		following.add(subject.getUsername());

		issuer.setFollowing(following);
		subject.setFollowers(followers);

		userRepository.save(subject);
		return userRepository.save(issuer);
	}

	public User denyFollower(String issuerId, String subjectId) {
		User subject = userRepository.findByUsername(subjectId);
		User issuer = userRepository.findByUsername(issuerId);

		Set<String> followers = subject.getFollowers();

		followers.remove(issuer.getUsername());

		subject.setFollowers(followers);

		userRepository.save(subject);
		return userRepository.save(issuer);
	}

	public List<String> getFriends(String username) {
		User user = userRepository.findByUsername(username);
		Set<String> following = user.getFollowing();
		List<String> usernames = new ArrayList<>();

		for (String u : following) {
			usernames.add(u);
		}

		return usernames;
	}

	public Set<String> getFollowRequests(String username) {
		User user = userRepository.findByUsername(username);
		Set<String> followRequests = user.getFollowers();
		return followRequests;
	}

	public CheckFollowDTO checkFollowing(String username, String subjectId) {
		User issuer = userRepository.findByUsername(username);
		Set<String> following = issuer.getFollowing();
		for (String users : following) {
			if (users.equals(subjectId)) {
				CheckFollowDTO follow = new CheckFollowDTO(subjectId, true);
				return follow;
			}
		}
		CheckFollowDTO follow = new CheckFollowDTO(subjectId, false);
		return follow;
	}

	public CheckFollowDTO checkFollowRequest(String username, String subjectId) {
		Set<String> followRequest = getFollowRequests(subjectId);
		for (String users : followRequest) {
			if (users.equals(username)) {
				CheckFollowDTO follow = new CheckFollowDTO(subjectId, true);
				return follow;
			}
		}
		CheckFollowDTO follow = new CheckFollowDTO(subjectId, false);
		return follow;
	}

	public CheckFollowDTO checkYourPage(String username, String subjectId) {
		if (username.equals(subjectId)) {
			CheckFollowDTO follow = new CheckFollowDTO(subjectId, true);
			return follow;
		} else {

			CheckFollowDTO follow = new CheckFollowDTO(subjectId, false);
			return follow;
		}
	}

	public List<User> search(String query) {
		return userRepository.search(query);
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

}
