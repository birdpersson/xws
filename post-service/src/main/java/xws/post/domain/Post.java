package xws.post.domain;

import javax.persistence.*;
import java.util.*;

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
	private List<String> mediaUrls = new ArrayList<>();

	@Column
	private String caption;

	@Column
	private String location;

	@ElementCollection
	private List<String> hashtags = new ArrayList<>();

	@ElementCollection
	private Set<String> likes = new HashSet<>();

	@ElementCollection
	private Set<String> dislikes = new HashSet<>();

	@OneToMany
	private List<Comment> comments = new ArrayList<>();

	@ElementCollection
	private List<String> sharedWith = new ArrayList<>();

	@Column
	private boolean highlighted;

	private String postType;

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}

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

	public List<String> getMediaUrls() {
		return mediaUrls;
	}

	public void setMediaUrls(List<String> mediaUrls) {
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

	public List<String> getHashtags() {
		return hashtags;
	}

	public void setHashtags(List<String> hashtags) {
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

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<String> getSharedWith() {
		return sharedWith;
	}

	public void setSharedWith(List<String> sharedWith) {
		this.sharedWith = sharedWith;
	}

	public boolean getHighlighted() {
		return highlighted;
	}

	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
	}
}
