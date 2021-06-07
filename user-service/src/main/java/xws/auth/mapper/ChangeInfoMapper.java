package xws.auth.mapper;

import xws.auth.domain.User;
import xws.auth.dto.ChangeInfo;

public class ChangeInfoMapper {

    public static ChangeInfo userToChangeInfo(User user){
        ChangeInfo dto = new ChangeInfo();
        dto.setDate(user.getBirthday());
        dto.setPhone(user.getPhone());
        dto.setGender(user.getGender());
        dto.setBio(user.getBio());
        dto.setUsername(user.getUsername());
        dto.setWebsite(user.getWebsite());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());

        return dto;
    }
}
