package xws.auth.mapper;

import xws.auth.domain.User;
import xws.auth.dto.ProfileDTO;

public class ChangeInfoMapper {

    public static ProfileDTO userToChangeInfo(User user){
        ProfileDTO dto = new ProfileDTO();
        dto.setDate(user.getBirthday());
        dto.setPhone(user.getPhone());
        dto.setGender(user.getGender());
        dto.setBio(user.getBio());
        dto.setUsername(user.getUsername());
        dto.setWebsite(user.getWebsite());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setPrivacy(user.isPrivate());
        dto.setAllowMessages(user.getAllowMessages());
        dto.setAllowTags(user.getAllowTags());

        return dto;
    }
}
