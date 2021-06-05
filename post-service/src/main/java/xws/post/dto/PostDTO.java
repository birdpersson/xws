package xws.post.dto;

import java.util.Set;

public class PostDTO {

	private String username;
	private String created;
	private Set<String> mediaUrls;

	private String caption;
	private String location;
	private Set<String> hashtags;

	private Set<String> sharedWith;
	private boolean isHighlighted;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
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
