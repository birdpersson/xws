package xws.auth.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "USERS")
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String username;

	@JsonIgnore
	@Column(nullable = false)
	private String password;

	@Column
	private String role;

	@Column
	private String name;

	@Column
	private String email;

	@Column
	private String phone;

	@Column
	private String gender;

	@Column
	private Date birthday;

	@Column
	private String website;

	@Column
	private String bio;

	@Column(nullable = false)
	private boolean enabled;

	@Column
	private boolean verified;

	@Column
	private boolean privacy;

	@Column
	private boolean allowMessages;

	@Column
	private boolean allowTags;

	@OneToMany
	private List<User> muted;

	@OneToMany
	private List<User> blocked;

	@ElementCollection
	private Set<String> following = new HashSet<>();

	@ElementCollection
	private Set<String> followers = new HashSet<>();

	@OneToMany
	private List<Notifications> settings;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_authority",
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
	private List<Authority> authorities;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public boolean isPrivate() {
		return privacy;
	}

	public void setPrivacy(boolean privacy) {
		this.privacy = privacy;
	}

	public boolean getAllowMessages() {
		return allowMessages;
	}

	public void setAllowMessages(boolean allowMessages) {
		this.allowMessages = allowMessages;
	}

	public boolean getAllowTags() {
		return allowTags;
	}

	public void setAllowTags(boolean allowTags) {
		this.allowTags = allowTags;
	}

	public List<User> getMuted() {
		return muted;
	}

	public void setMuted(List<User> muted) {
		this.muted = muted;
	}

	public List<User> getBlocked() {
		return blocked;
	}

	public void setBlocked(List<User> blocked) {
		this.blocked = blocked;
	}

	public Set<String> getFollowing() {
		return following;
	}

	public void setFollowing(Set<String> following) {
		this.following = following;
	}

	public Set<String> getFollowers() {
		return followers;
	}

	public void setFollowers(Set<String> followers) {
		this.followers = followers;
	}

	public List<Notifications> getSettings() {
		return settings;
	}

	public void setSettings(List<Notifications> settings) {
		this.settings = settings;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

}