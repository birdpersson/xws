package xws.post.dto;

import java.util.Date;

public class CommentDTO {
    String text;
    String username;
    Date date;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public CommentDTO(String username, String text, Date date){
        this.username = username;
        this.text = text;
        this.date = date;
    }
}
