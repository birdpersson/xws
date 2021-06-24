package xws.post.mapper;

import xws.post.domain.Post;
import xws.post.dto.GetPostDTO;

import java.util.Date;
import java.util.List;

public class PostMapper {

    public static GetPostDTO postToGetPostDTO(Post p){
        Long id = p.getId();
        String username = p.getUsername();
        String location = p.getLocation();
        String description =p.getCaption();
        List<String> hashtags = p.getHashtags();
        List<String> mediaUrls = p.getMediaUrls();
        Date date = p.getCreated();
        GetPostDTO dto = new GetPostDTO(id, username,location,description,hashtags, date,mediaUrls);

        return dto;
    }
}
