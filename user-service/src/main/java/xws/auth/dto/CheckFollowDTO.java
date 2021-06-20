package xws.auth.dto;



public class CheckFollowDTO {
    private String username;
    private Boolean follow;

    public CheckFollowDTO(){}

    public CheckFollowDTO(String username, Boolean follow){
        this.username = username;
        this.follow = follow;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getFollow() {
        return follow;
    }

    public void setFollow(Boolean follow) {
        this.follow = follow;
    }
}
