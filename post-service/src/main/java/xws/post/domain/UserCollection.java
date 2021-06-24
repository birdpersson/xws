package xws.post.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class UserCollection {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String username;

	@OneToMany
	private Set<Post> likes = new HashSet<>();

	@OneToMany
	private Set<Post> dislikes = new HashSet<>();

	@OneToMany
	private Set<Post> favorites = new HashSet<>();

	@OneToMany
	private Set<CustomCollection> collections = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<Post> getLikes() {
		return likes;
	}

	public void setLikes(Set<Post> likes) {
		this.likes = likes;
	}

	public Set<Post> getDislikes() {
		return dislikes;
	}

	public void setDislikes(Set<Post> dislikes) {
		this.dislikes = dislikes;
	}

	public Set<Post> getFavorites() {
		return favorites;
	}

	public void setFavorites(Set<Post> favorites) {
		this.favorites = favorites;
	}

	public Set<CustomCollection> getCollections() {
		return collections;
	}

	public void setCollections(Set<CustomCollection> collections) {
		this.collections = collections;
	}

}
