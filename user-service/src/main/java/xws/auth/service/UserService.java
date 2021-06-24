package xws.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xws.auth.domain.Authority;
import xws.auth.domain.User;
import xws.auth.dto.ChangeInfo;
import xws.auth.dto.CheckFollowDTO;
import xws.auth.dto.UserRegistrationDTO;
import xws.auth.exception.UsernameNotUniqueException;
import xws.auth.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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



	public User updateInfo(ChangeInfo dto, User user){


		user.setBio(dto.getBio());
		user.setEmail(dto.getEmail());
		user.setUsername(dto.getUsername());
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

//		List<Authority> auth = authService.findByName("ROLE_USER");
//		u.setAuthorities(auth);

		u = userRepository.save(u);
		return u;
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

	public List<String> getFriends(String username){
		User user = userRepository.findByUsername(username);
		Set<String> following = user.getFollowing();
		List<String> usernames = new ArrayList<String>();

		for(String u : following){
			usernames.add(u);
		}

		return usernames;
	}


	public Set<String> getFollowRequests(String username){
		User user = userRepository.findByUsername(username);
		Set<String> followRequests = user.getFollowers();
		return followRequests;
	}

	public CheckFollowDTO checkFollowing(String username, String subjectId){
		User issuer = userRepository.findByUsername(username);
		Set<String> following = issuer.getFollowing();
		for(String users : following){
			if(users.equals(subjectId)){
				CheckFollowDTO follow = new CheckFollowDTO(subjectId, true);
				return follow;
			}
		}
		CheckFollowDTO follow = new CheckFollowDTO(subjectId, false);
		return follow;


	}

	public CheckFollowDTO checkFollowRequest(String username, String subjectId){
		Set<String> followRequest = getFollowRequests(subjectId);
		for(String users : followRequest){
			if(users.equals(username)){
				CheckFollowDTO follow = new CheckFollowDTO(subjectId, true);
				return follow;
			}
		}
		CheckFollowDTO follow = new CheckFollowDTO(subjectId, false);
		return follow;


	}

	public CheckFollowDTO checkYourPage(String username, String subjectId){
			if(username.equals(subjectId)){
				CheckFollowDTO follow = new CheckFollowDTO(subjectId, true);
				return follow;
			}else {

				CheckFollowDTO follow = new CheckFollowDTO(subjectId, false);
				return follow;
			}


	}


  	public List<User> search(String query){
		return userRepository.search(query);

	}

	public List<User> findAll(){
		return userRepository.findAll();
	}

}
