package xws.auth.domain;

import javax.persistence.*;

@Entity
public class Notifications {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String username;

	@Column
	private boolean stories;

	@Column
	private boolean posts;

	@Column
	private boolean comments;

	@Column
	private boolean messages;

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

	public boolean getStories() {
		return stories;
	}

	public void setStories(boolean stories) {
		this.stories = stories;
	}

	public boolean getPosts() {
		return posts;
	}

	public void setPosts(boolean posts) {
		this.posts = posts;
	}

	public boolean getComments() {
		return comments;
	}

	public void setComments(boolean comments) {
		this.comments = comments;
	}

	public boolean getMessages() {
		return messages;
	}

	public void setMessages(boolean messages) {
		this.messages = messages;
	}

}
