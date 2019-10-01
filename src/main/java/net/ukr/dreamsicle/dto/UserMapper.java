package net.ukr.dreamsicle.dto;

import net.ukr.dreamsicle.model.User;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Mapper
@Service
public interface UserMapper {

    UserDTO toUserDto(User user);

    User toUser(UserDTO userDTO);

    default Page<UserDTO> toUserDTOs(Page<User> users) {
        return users.map(this::toUserDto);
    }
}
