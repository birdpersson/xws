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
	private List<Post> likes = new ArrayList<>();

	@OneToMany
	private List<Post> dislikes = new ArrayList<>();

	@OneToMany
	private List<Post> favorites = new ArrayList<>();

	@OneToMany
	private List<CustomCollection> collections = new ArrayList<>();

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

	public List<Post> getLikes() {
		return likes;
	}

	public void setLikes(List<Post> likes) {
		this.likes = likes;
	}

	public List<Post> getDislikes() {
		return dislikes;
	}

	public void setDislikes(List<Post> dislikes) {
		this.dislikes = dislikes;
	}

	public List<Post> getFavorites() {
		return favorites;
	}

	public void setFavorites(List<Post> favorites) {
		this.favorites = favorites;
	}

	public List<CustomCollection> getCollections() {
		return collections;
	}

	public void setCollections(List<CustomCollection> collections) {
		this.collections = collections;
	}

}
