package xws.auth.dto;

public class NotificationDTO {
	private boolean stories;
	private boolean posts;
	private boolean comments;
	private boolean messages;

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
