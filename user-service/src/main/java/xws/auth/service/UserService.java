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
import xws.auth.domain.User;
import xws.auth.dto.ChangeInfo;
import xws.auth.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

	public List<User> findAll(){
		return userRepository.findAll();
	}

}
