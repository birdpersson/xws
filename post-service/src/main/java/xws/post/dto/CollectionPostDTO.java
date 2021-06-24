package xws.post.dto;

import java.util.Date;
import java.util.List;

public class CollectionPostDTO {
    private Long id;
    private Date date;
    List<String> media;

    public CollectionPostDTO(Long id, Date date, List<String> media) {
        this.id = id;
        this.date = date;
        this.media = media;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<String> getMedia() {
        return media;
    }

    public void setMedia(List<String> media) {
        this.media = media;
    }
}
