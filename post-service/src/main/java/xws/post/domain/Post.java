package xws.post.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String username;

	@Column
	private Date created;

	@ElementCollection
	private Set<String> mediaUrls = new HashSet<>();

	@Column
	private String caption;

	@Column
	private String location;

	@ElementCollection
	private Set<String> hashtags = new HashSet<>();

	@ElementCollection
	private Set<String> likes = new HashSet<>();

	@ElementCollection
	private Set<String> dislikes = new HashSet<>();

	@OneToMany
	private Set<Comment> comments = new HashSet<>();

	@ElementCollection
	private Set<String> sharedWith = new HashSet<>();

	@Column
	private boolean isHighlighted;

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

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Set<String> getMediaUrls() {
		return mediaUrls;
	}

	public void setMediaUrls(Set<String> mediaUrls) {
		this.mediaUrls = mediaUrls;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Set<String> getHashtags() {
		return hashtags;
	}

	public void setHashtags(Set<String> hashtags) {
		this.hashtags = hashtags;
	}

	public Set<String> getLikes() {
		return likes;
	}

	public void setLikes(Set<String> likes) {
		this.likes = likes;
	}

	public Set<String> getDislikes() {
		return dislikes;
	}

	public void setDislikes(Set<String> dislikes) {
		this.dislikes = dislikes;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Set<String> getSharedWith() {
		return sharedWith;
	}

	public void setSharedWith(Set<String> sharedWith) {
		this.sharedWith = sharedWith;
	}

	public boolean isHighlighted() {
		return isHighlighted;
	}

	public void setHighlighted(boolean highlighted) {
		isHighlighted = highlighted;
	}

}
