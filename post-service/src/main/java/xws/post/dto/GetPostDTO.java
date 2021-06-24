package xws.post.dto;

import java.util.Date;
import java.util.List;

public class GetPostDTO {
    private  Long id;
    private String username;
    private String location;
    private String description;
    private List<String> hashtags;
    private List<String> mediaUrls;
    private Date date;

    public GetPostDTO(Long id, String username, String location, String description, List<String> hashtags, Date date, List<String> mediaUrls) {
        this.id = id;
        this.username = username;
        this.location = location;
        this.description = description;
        this.hashtags = hashtags;
        this.date = date;
        this.mediaUrls = mediaUrls;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getMediaUrls() {
        return mediaUrls;
    }

    public void setMediaUrls(List<String> mediaUrls) {
        this.mediaUrls = mediaUrls;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<String> hashtags) {
        this.hashtags = hashtags;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
